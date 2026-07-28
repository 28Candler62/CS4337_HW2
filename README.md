## CS4337: Human-Computer-Interaction
## HW 2: Build an interface using Android Studio

In this assignment, you will use Android Studio to build an App’s interface (without functions). The objective is to get
familiar with views, layouts, and attribute settings.
The App contains a calculator and a calendar. The default text on the upper region of the whole screen will be “HW2
by FirstName LastName”. Here you should use your name in this string.
Please **submit the .xml file** of the screen and ***three screenshots** (like the below images) including:
1. the App interface on an emulator/real device (left),
2. the App on the background of the Android system (middle), and
3. the design view in Android Studio (right).
   
To be specific,
- The App interface should contain three parts: the text view, the calculator buttons, and the calendar (30 pts)
- The whole screen should have top, bottom, left, and right margins set to 20dp (5 pts)
- The text of the TextView should align to the center (5 pts)
- The calculator buttons (including digits and text on them) should be placed exactly according to the shown layout (25 pts)
- The calendar shows your submission date (5 pts)
- The screenshots of running the App (i.e. the left and the middle) (30 pts).

![HW2 three screenshoots example](images/hw2_example.png)

## Implementation Notes

Although the original assignment requested an XML layout using the Android View system, this project was implemented using **Jetpack Compose**, which is Google's recommended modern UI toolkit for Android development. The interface replicates the required calculator and calendar layout while using declarative Compose components instead of XML layouts.

In addition to the original assignment requirements, the application includes the following improvements:

- **Responsive layout:** The UI adapts correctly to both portrait and landscape orientations, maintaining the required layout and spacing after screen rotation.
- **Display cutout (camera) support:** The top app bar and content are positioned to avoid overlap with devices that have a centered front-facing camera (display cutout), ensuring the title remains visible and properly aligned.
- **Modern Android practices:** The project uses Compose layouts, Material components, and state management rather than the legacy View-based approach.

### Deliverables

The project includes:
- A Jetpack Compose implementation of the required interface.
- Screenshots of the application running in portrait mode.
- Screenshots of the application running in landscape mode after device rotation.
- Screenshots of the application displayed in the Android app switcher/background.

![Screenshoot: preview in portrait mode](images/Preview_Portrait.png) ![Screenshoot: emulator in portrait mode](images/Emulator_Portrate.png) ![Screenshoot: emulator in app switcher](images/Emulator_Selector.png)
![Screenshoot: emulator in landscape mode](images/Emulator_Landscape.png)
![Screenshoot: preview in landscape mode](images/Preview_Landscape.png)
