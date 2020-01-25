# ------------------------------------

#Provide the name of the snapshot to duplicate
$snapshotName = 'FICTestingVMDev_Source'  

#Provide the size of the virtual machine
$virtualMachineSize = 'Standard_D4s_v3'

#Provide the number of Virtual Machines to create
$nbVMToCreate = 2

# ------------------------------------

$resourceGroupNameSrc = 'FIC_Testing' 
$resourceGroupName = 'FIC_Testing_TP' 
$location = 'francecentral' 

# ------------------------------------

#Enter the name of an existing virtual network where virtual machine will be created
$virtualNetworkName = 'FIC_Testing_TP-vnet'

#Name of Subnet
$subnetName = 'FIC_Testing_TP-subnet'

#Enter the name of the virtual machine to be created
$virtualMachineNameRoot = 'VMTPFICTesting'

# ------------------------------------

Write-Output ("About to create "+$nbVMToCreate+" virtual machines of size "+$virtualMachineSize+" in resource group "+$resourceGroupName+" as copy of snapshot "+$snapshotName)

#Get Snapshot
$snapshot = Get-AzureRmSnapshot -ResourceGroupName $resourceGroupNameSrc -SnapshotName $snapshotName 

Write-Output ("About to create virtual network "+$virtualNetworkName)

#Create virtual network
az network vnet create --resource-group $resourceGroupName `
	--location $location --name $virtualNetworkName `
	--address-prefix 192.168.0.0/16 `
	--subnet-name $subnetName `
	--subnet-prefix 192.168.1.0/24

# Get virtual network
$vnet = Get-AzureRmVirtualNetwork -Name $virtualNetworkName -ResourceGroupName $resourceGroupName
	
# For Each VM
For ($i=1; $i -le $nbVMToCreate; $i++) {

	$virtualMachineName = $virtualMachineNameRoot+"_"+$i
	
	Write-Output ("--------------------------------------------------")
	Write-Output ("Starting creation of VM "+$i+" named "+ $virtualMachineName)
	
	Write-Output ("About to create OS Disk "+$osDiskName+" for VM "+$i)

	#Create managed disk
	$osDisk = New-AzDisk -DiskName ($VirtualMachineName+"_osdisk") -Disk `
		(New-AzDiskConfig  -Location $location -CreateOption Copy `
		-SourceResourceId $snapshot.Id) `
		-ResourceGroupName $resourceGroupName
		
	#Initialize virtual machine configuration
	$VirtualMachine = New-AzureRmVMConfig -VMName $virtualMachineName -VMSize $virtualMachineSize

	#Use the Managed Disk Resource Id to attach it to the virtual machine. Use OS type based on the OS present in the disk - Windows / Linux
	$VirtualMachine = Set-AzureRmVMOSDisk -VM $VirtualMachine -ManagedDiskId $osDisk.Id -CreateOption Attach -Windows

	Write-Output ("About to create public IP for VM "+$i)
	#Create a public IP 
	$publicIp = New-AzureRmPublicIpAddress -Name ($VirtualMachineName+'_ip') -ResourceGroupName $resourceGroupName -Location $snapshot.Location -AllocationMethod Dynamic

	Write-Output ("About to create network interface for VM "+$i)
	# Create NIC for the VM
	$nic = New-AzureRmNetworkInterface -Name ($VirtualMachineName+'_nic') -ResourceGroupName $resourceGroupName -Location $snapshot.Location -SubnetId $vnet.Subnets[0].Id -PublicIpAddressId $publicIp.Id

	$VirtualMachine = Add-AzureRmVMNetworkInterface -VM $VirtualMachine -Id $nic.Id

	#Create the virtual machine with Managed Disk
	Write-Output ("About to create VM "+$virtualMachineName+" for VM "+$i)
	New-AzureRmVM -VM $VirtualMachine -ResourceGroupName $resourceGroupName -Location $snapshot.Location -AsJob

}