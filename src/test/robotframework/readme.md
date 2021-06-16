# How to install Robot framework and execute tests

## Prerequisites

- Python3
- Chrome web browser

## Install RobotFramework

- Install or upgrade PIP `python -m pip install --upgrade pip`
- Then install Robot Framework `pip install robotframework`
- Add support of selenium `pip install --upgrade robotframework-seleniumlibrary`
- Add support of Web Driver Manager (headless browsers) `pip install webdrivermanager`
- Download headless Firefox and Chrome to c:\dev\software `webdrivermanager firefox chrome --linkpath c:\dev\software`
- Add `c:\dev\software` to Windows Path

## Execute tests

- At first start the application to test
- Open a terminal
- Go into **src/test/robotframework** folder with `cd src/test/robotframework`
- Execute Robot tests with command `robot --outputdir executionResults/ runBasicWebShoppingTests.robot`
- A Chrome window will open and you will see the execution of your tests
- When tests are done, you may look at detailed execution results in in file **executionResults/report.html**
