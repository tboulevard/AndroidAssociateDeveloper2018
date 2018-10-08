# AndroidAssociateDeveloper2018

Repo for tackling the Android Associate Developer Exam in 2018.

## Exam Content

Mark these off as they are completed ~like this~

### Testing and debugging
- Write and execute local JVM unit tests
- Write and execute Android UI tests
- ~Use the system log to output debug information~
- ~Debug and fix issues with an app's functional behavior and usability~

### User interface (UI) and app functionality
- ~Create an Activity that displays a layout~
- ~Construct a UI with ConstraintLayout~
- Create a custom view class and add it to a layout
- Add accessibility hooks to a custom view
- Apply content descriptions to views for accessibility
- ~Implement a custom app theme~
- Display items in a RecyclerView
- Bind local data to a RecyclerView list using the paging library
- ~Implement menu-based or drawer navigation~
- Localize the app
- Display notifications, toasts, and snackbar messages
- Schedule a background task using JobScheduler
- Efficiently run jobs in the background

### App data and files
- Define data using Room entities
- Access Room database with data access object (DAO)
- Observe and respond to changing data using LiveData
- Use a Repository to handle data operations
- Read and parse raw resources or asset files
- Create persistent preference data from user input
- Change the behavior of the app based on user preferences

### Lesson Notes

**Lession 5**
- Prefer webp & PNG images for Drawable resources
- A 9-patch is a PNG image in which you define stretchable regions. Use a 9-patch as the background image for a View to make sure the View looks correct for different screen sizes and orientations. Save 9-patch files with a .9.png extension and store them in the res/drawable folder. Use them with the android:src attribute for an ImageView and its descendants, or to create a NinePatchDrawable class in Java code.
- What's the difference between a style and a theme?
   > A style applies to a View. In XML, you apply a style using the style attribute.
   > A theme applies to an Activity or an entire app, rather than to an individual View. In XML, you apply a theme using the android:theme attribute.
   > **Note:** Any style can be used as a theme. For example, you could apply the CodeFont style as a theme for an Activity, and all the text inside the Activity would use gray monospace font.


## Resources

[Exam Sign-up / Content Page](https://developers.google.com/training/certification/associate-android-developer/)

[Android Developer Fundamentals (Version 2)](https://google-developer-training.github.io/android-developer-fundamentals-course-concepts-v2/)

## IMPORTANT NOTES

- If you plan to take the exam at a certain predefined time (for me it was Saturday 10 am), **I’d suggest you do the payment, subscription, and ID verification at least one day before taking it.** Actually, after you pay, you’ll be asked to scan either a passport or a driving license (not all countries driving licenses are supported), once you do you’ll have to wait for up to few hours to get verified. You won’t be able to take the exam before getting your ID verified.


