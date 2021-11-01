---
layout: page
title: User Guide
---

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Introduction

**Tuition Address Book (TAB)** is an all-in-one desktop application that helps you keep track of the large number of students and their respective lesson information, and empower you to provide the best quality home tuition service.

TAB comes with a clean Graphical User Interface (GUI) while optimised for user interaction via a CLI (Command Line Interface).
With TAB, you can effortlessly manage your students' contact details faster than a typical mouse/GUI driven app.

--------------------------------------------------------------------------------------------------------------------

## About This Guide

This user guide is intended for any user of TAB. It provides installation instructions to help get you started with using TAB, and describes in detail the features available in TAB.

**Navigation**

In the [Table of Contents](), each item listed is a link which you can click on to go directly to that section in the guide.

**Conventions Used**

* `text` : denotes a command to be entered into the command box in TAB.
* <kbd>text</kbd> : denotes a keyboard input, or a button to be clicked on.
* [text](#about-this-guide) : denotes links to other parts of the document, or links to be opened in the browser.

--------------------------------------------------------------------------------------------------------------------

## Quick Start

<div markdown="block" class="alert alert-info">

**:information_source: JDK Installation Guide**

You can install the required JDK and JRE from the
[Java SE Development Kit Downloads page](https://www.oracle.com/java/technologies/downloads/).

Here is a [website](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A)
to help you with the installation. Follow the guide for your operating system for detailed instructions.
</div>


1. Ensure you have **Java 11** or above installed on your computer.

2. Download the latest **TAB.jar** from [here](https://github.com/AY2122S1-CS2103T-F13-3/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your TAB.

4. Double-click the file to start the app. The window similar to the one below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press <kbd>ENTER</kbd> to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all students.

   * **`add`**`n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a student named `John Doe` to TAB.

   * **`delete`**`3` : Deletes the 3rd student shown in the current list.

   * **`clear`** : Deletes all students.

   * **`exit`** : Exits the app.

6. Refer to the [Features](#features) section for details of each command.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## Features
This section describes the available features in TAB.

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.
  
* Items in curly brackets separated by the pipe character `|` indicates that you must select exactly one parameter from the list of choices.
  e.g. `cond/{all | any | none}` can be used as `cond/all` or `cond/any` or `cond/none`.
  
* Items with `…` after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command, but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.
  
* There are constraints in place to determine whether the value you provided for a field is valid. 
  TAB will inform you if you gave an invalid input for a field.

</div>

<div style="page-break-after: always;"></div>

### Getting Help 
This section tells you what to do if you require help while using TAB.

--------------------------------------------------------------------------------------------------------------------

#### Viewing help: `help`

Shows a command summary table as well as a link to access this user guide page.

Format: `help`

- You can select a cell and press <kbd>CONTROL</kbd> + <kbd>C</kbd> or <kbd>COMMAND</kbd> + <kbd>C</kbd> on your keyboard to copy the selected cell value.
- You can click <kbd>Copy URL</kbd> button to copy the link to this user guide.
- You can click the right end to each column to sort the rows alphabetically.

![help message](images/helpMessage.png)
<div class="caption">Help window interface.</div>

<div style="page-break-after: always;"></div>

### Managing Students
This section guides you on how to use the commands for managing students in TAB.

A student must have the following essential fields:
* Name
* Address

A student must have **at least 1** of these contact fields:
* Phone number
* Parent phone number
* Email
* Parent email

Other available optional fields for a student are:
* Academic level
* Academic stream
* School
* Outstanding fees
* Remarks
* Tags
* Lessons

<div markdown="block" class="alert alert-info">
**:information_source: Note:**<br>
A student can be identified by the index number shown in the displayed list of students.
</div>

--------------------------------------------------------------------------------------------------------------------

#### Adding a student: `add`

Adds a student to TAB.

Format: `add n/NAME a/ADDRESS [p/PHONE_NUMBER] [e/EMAIL] [pp/PARENT_PHONE_NUMBER] [pe/PARENT_EMAIL] [sch/SCHOOL] [stream/ACAD_STREAM] [lvl/ACAD_LEVEL] [f/OUTSTANDING_FEES] [r/REMARK] [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A student can have any number of tags (including 0).
</div>

* At least one contact field is required.<br>
  e.g. at least one of the `p/PHONE_NUMBER`, `e/EMAIL`, `pp/PARENT_PHONE_NUMBER`, or `pe/PARENT_EMAIL` fields must be 
  included in the add command.
* `lvl/ACADEMIC_LEVEL` field allows only a maximum of 15 characters (including spaces).

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 pp/92345678 pe/jackdoe@example.com sch/John's School stream/John stream lvl/J1`
* `add n/Betsy Crowe t/cousin a/Newgate p/91234567 f/150.50 r/hasnt pay tuition fee for Aug t/retainee`

#### Editing a student: `edit`

Edits an existing student in TAB.

Format: `edit INDEX [n/NAME] [a/ADDRESS] [p/PHONE] [e/EMAIL] [pp/PARENT_PHONE_NUMBER] [pe/PARENT_EMAIL] [sch/SCHOOL] [stream/ACAD_STREAM] [lvl/ACAD_LEVEL] [f/OUTSTANDING_FEES] [r/REMARK] [t/TAG]…​`

* Edits the student at the specified `INDEX`. <br>
  e.g. `edit 2` means that you wish to edit the 2nd student in the displayed list.

* You must provide at least one field.<br>
  e.g. entering just `edit 2` alone is not a valid command. You need to include the field you wish to edit.

* Existing values will be updated to the entered values.<br>
  e.g. `edit 2 f/0` will override the outstanding fees of the 2nd student in the displayed list to `0`.

* When editing tags, all existing tags of the student will be removed and replaced with the tags specified.<br>
  e.g. `edit 2 t/SEC2 t/IP` will erase the student's original tags and replace it with the new tags `SEC2` and `IP`.

* You can delete the data in optional fields by supplying a parameter with no arguments.<br>
  e.g. `edit 2 r/` will remove the remarks for the 2nd student in the displayed list.

* You cannot remove a contact field if it is the only remaining means of contact you have with a student.<br>
  e.g. no student should have all contact fields empty. `edit 2 pp/` will not work if the student does not have
  any `PHONE_NUMBER`, `EMAIL`, or `PARENT_EMAIL`.

* You can delete all tags of a student by typing `t/` without any arguments.<br>
  e.g. `edit 2 t/` will remove all existing tags from the 2nd student in the displayed list.

Examples:
* `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.
* `edit 2 n/Betsy Crower t/` Edits the name of the 2nd student to be `Betsy Crower` and clears all existing tags.
* `edit 3 sch/NJC stream/` Edits the school of the 3rd student to be `NJC` and clears academic stream data.

#### Deleting a student: `delete`

Deletes the specified student from TAB.

Format: `delete INDEX`

* Deletes the student at the specified `INDEX`.

* The index must be a valid index number of a student shown in the displayed list.

Examples:
* `list` followed by `delete 2` deletes the 2nd student in TAB.
* `find n/Betsy` followed by `delete 1` deletes the 1st student in the results of the `find` command.

#### Listing all students: `list`

Shows a list of all students in TAB.

Format: `list`

<div style="page-break-after: always;"></div>

### Finding Students
This section guides you on how to find or filter students of your choice in TAB.

--------------------------------------------------------------------------------------------------------------------

#### Finding students by fields: `find`

Finds all students whose fields match the given keyword(s), based on the specified find condition.

Format: `find [cond/{all | any | none}] [n/NAME_KEYWORDS] [a/ADDRESS_KEYWORDS] [p/PHONE_KEYWORDS] [e/EMAIL_KEYWORDS] [pp/PARENT_PHONE_KEYWORDS] [pe/PARENT_EMAIL_KEYWORDS] [sch/SCHOOL_KEYWORDS] [stream/ACAD_STREAM_KEYWORDS] [lvl/ACAD_LEVEL_KEYWORDS] [t/TAG_KEYWORD]…​`

Notes about the find condition:

* The find condition indicates that a student is only considered a match when `all`, `any` or `none`
  of the fields which you are searching for match the student.<br>
  e.g. 
    * `find n/John t/math cond/all` will return students with both the name `John` and the tag `math`.
    * `find n/John t/math cond/any` will return students with only the name `John`, or only the tag `math`, or both.
    * `find n/John t/math cond/none` will return students without the name `John` and the tag `math`.

* The find condition will not accept other arguments besides `all`, `any` and `none`.<br>
  e.g. `cond/every` will result in an error.
  
* The find condition is optional and defaults to `all` if not specified.

* The find condition is case-insensitive.<br>
  e.g. `None` or `ANY` are valid.
  
Notes about search keywords:

* You must provide at least one field to search.<br>
  e.g. entering just `find` or `find cond/any` alone is not a valid command. You need to include the fields you wish to search for.

* You must provide at least one keyword to search for.<br>
  e.g. entering just `find n/` alone is not a valid command as the keyword is empty.

* Tags must only have one keyword.<br>
  e.g. `find t/zoom math` is invalid. To search by multiple tags, you can do `find t/zoom t/math`.
  
* The search is case-insensitive.<br>
  e.g. keyword `hans` will match `Hans`.

* A keyword can match a word partially.<br>
  e.g. keyword `math` will match `mathematics`.
  
* The order of the keywords do not matter.<br>
  e.g. keyword `west jurong` will match `jurong west`.
  
* A field needs to contain all specified keywords to be matched.<br>
  e.g. keywords `Amad Ali` will not match `Amad` or `Ali Abdul`, but it will match `Amad bin Ali`.

Examples:

The figure below shows TAB with four students in the list.
<div align="center">
  <img class="figure" src="images/FindStudents1.png" width="450px" alt="find initial list"/>
</div>
<div class="caption">Initial list of students displayed in TAB</div>

To find a student, you may enter `find a/serangoon n/Bern` into the command box.

All students in TAB whose address matches `serangoon` and whose name matches `Bern` will be returned. The figure below shows the list after the find command is executed.

<div align="center">
  <img class="figure" src="images/FindStudents2.png" width="400px" alt="find eg1"/>
</div>
<div class="caption">TAB displays one student after the find command.</div>

To find students without the `unpaid` tag and whose school is not `NYJC`, you may enter the command `find cond/none t/unpaid sch/NYJC`. The figure below shows the list after this find command is executed.

<div align="center">
  <img class="figure" src="images/FindStudents3.png" width="400px" alt="find eg2"/>
</div>
<div class="caption">TAB displays two students after the find command.</div>

<div style="page-break-after: always;"></div>

### Managing Lessons

This section guides you on how to use the commands for managing the lessons of your students in TAB.
A lesson **must** have the following fields: a start date, a time range, a lesson rate and a subject.

A lesson can be categorised into 2 types: 
1. A **weekly** recurring lesson
2. A one-off makeup lesson.

The essential fields for a lesson are:
* Date
* Time range
* Subject
* Rate

<div markdown="block" class="alert alert-info">
**:information_source: Note:**<br>
* The lesson's rate refers to the fee of the lesson per hour.
This rate will be used in the calculation of fees due after each lesson.

*  A lesson can be identified by the index number shown in the lesson list of the student.
</div>

An optional field for a lesson is:
* Homework
<br>

--------------------------------------------------------------------------------------------------------------------

#### Adding a lesson: `ladd`

Adds a lesson to the specified student in TAB.

Format: `ladd INDEX [recurring/] date/dd MMM yyyy time/HHmm-HHmm subject/SUBJECT rates/LESSON_RATES [hw/HOMEWORK]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
You can add multiple pieces of homework to a lesson in TAB.
</div>

* The type of lesson will be inferred from the presence of the `recurring/` prefix.
  
* The date is case-insensitive. i.e. `12 jaN 2022` is equivalent to `12 JAN 2022`.

Examples:

* `list` followed by `ladd 1 recurring/ date/30 jan 2022 time/0900-1100 subject/Math rates/37.50`
  adds the recurring lesson to the 1st student in the displayed student list.

* `list` followed by `ladd 4 date/16 Sep 2021 time/1530-1730 subject/Science hw/TYS p2 Q2 hw/Exercise 3 hw/Lab report rates/40`
  adds the makeup lesson to the 4th student in the displayed student list.

* `find n/john` followed by `ladd 1 date/30 MAR 2021 time/1630-1745 subject/Physics hw/Worksheet 1 rates/25.00`
  adds the makeup lesson to the 1st student in the results of the `find` command.

#### Editing a lesson : `ledit`

Edits the specified lesson of the specified student in TAB with the indicated changes for specified fields.

Format: `ledit INDEX LESSON_INDEX [time/TIMERANGE] [rates/RATE] [subject/SUBJECT] [hw/HOMEWORK]…​`

* Edits the lesson of specified `LESSON_INDEX` for the student at the specified `INDEX`.

* You can edit all fields of a lesson except the start date.

* You cannot change the lesson's type (i.e. recurring and makeup).

* The index must be a valid index number shown in the displayed student list.

* The lesson index must be a valid index number shown in the lesson list of the student.

Examples:
* `ledit 1 1 time/1100-1200` Edits the time range of the 1st lesson of the 1st student to be `1100-1200`.
* `ledit 2 3 rates/35.85 subject/Chinese hw/` Edits the subject of the 3rd lesson of the 2nd student to be `Chinese` and clears all existing homework.
* `ledit 3 1 hw/Textbook hw/Exercise 5` Edits the homework list of 1st lesson of the 3rd student to contain `Textbook` and `Exercise 5` only.

#### Deleting a lesson : `ldelete`

Deletes the specified lesson of the specified student in TAB.

Format: `ldelete INDEX LESSON_INDEX`

* Deletes the lesson of specified `LESSON_INDEX` for the student at the specified `INDEX`.
  
* The index must be a valid index number shown in the displayed student list.
  
* The lesson index must be a valid index number shown in the lesson list of the student.

Examples:
* `list` followed by `ldelete 2 1` deletes the 1st lesson for the 2nd student in TAB.
  
* `find n/Betsy` followed by `ldelete 1 1` deletes the 1st lesson for the 1st student in the results 
  of the `find` command.
  
#### Viewing lessons : `view`

Views all the lessons for the specified student in TAB.

Format: `view INDEX`

* Views the list of lessons belonging to the student of the specified `INDEX`.

* The index must be a valid index number shown in the displayed student list.


Examples:

* `view 2` Displays the list of lessons for the 2nd student in the displayed student list.

* `find n/Betsy` followed by `view 1` displays the list of lessons for the 1st student in the results of the `find` command.


<div style="page-break-after: always;"></div>

### Viewing your Calendar

This section guides you on how to use TAB's calendar interface. Typing any of the commands in this section will bring you to the calendar interface. Any other command, such as `list` or `tag`, will bring you right out.

[New GUI screenshots coming soon...]

--------------------------------------------------------------------------------------------------------------------

#### Switching to the calendar interface: `calendar`

Brings you to TAB's calendar interface for you to see all your scheduled lessons.

Format: `calendar`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
You don't always have to type `calendar` to switch to the calendar interface. All the other calendar commands in this section act as a shortcut that opens the calendar as well! 
</div>

[New GUI screenshots coming soon...]

#### Viewing your daily calendar: `day`

Displays the daily calendar, which shows your scheduled lessons for the day.

Format: `day`

[New GUI screenshots coming soon...]

#### Viewing your weekly calendar: `week`

Shows the weekly calendar for you to see all the lessons you have for the week

Format: `week`

[New GUI screenshots coming soon...]

#### Viewing your monthly calendar: `month`

Displays the monthly calendar, allowing you to visualise your lessons for the month.

Format: `month`

[New GUI screenshots coming soon...]

#### Viewing your weekly calendar: `year`

Shows the yearly calendar. You can see which days of the year you have lessons in this view.

Format: `year`

[New GUI screenshots coming soon...]

#### Navigating forward in the calendar: `next`

Goes to the next day/week/month/year in the calendar interface.

Format: `next`

#### Navigating backwards in the calendar: `back`

Goes to the previous day/week/month/year in the calendar.

Format: `back`

#### Navigate to today in the calendar: `today`

Jumps to the current day in the calendar.

Format: `today`

<div style="page-break-after: always;"></div>

### Managing Data

This section informs you on how data is handled in TAB.

--------------------------------------------------------------------------------------------------------------------

#### Saving the data

TAB data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

#### Editing the data file

TAB data are saved as a JSON file **[JAR file location]/data/addressbook.json**. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, TAB will discard all data and start with an empty data file at the next run.
</div>

<div style="page-break-after: always;"></div>

### Miscellaneous Commands
This section describes the other miscellaneous commands available in TAB.

--------------------------------------------------------------------------------------------------------------------

#### Clearing all entries: `clear`

Clears all entries from TAB.

Format: `clear`

#### Viewing all tags: `tag`

Shows all the tags that you have created together with the number of students labelled with each tag.

Format: `tag`

![taglist](images/taglist.png)
<div class="caption">The text on the left shows the tag names created and the number on the right indicates the number of students labelled with each tag.</div>

<div style="page-break-after: always;"></div>

#### Undoing previous command: `undo`

Undo the previous command that modified the data.

Format: `undo`

#### Redoing undone command: `redo`

Redo the previous command that has been undone.

Format: `redo`

#### Exiting the program: `exit`

Exits the program.

Format: `exit`

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## FAQ
This section records frequently asked questions from users of TAB.

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

**Q**: I am using a Mac and when I tried to open **TAB.jar**, Mac shows “TAB.jar cannot be opened because it is from an unidentified developer". How do I resolve this issue? <br>
**A**: Go to <kbd>System Preferences</kbd> → <kbd>Security & Privacy</kbd> → <kbd>General</kbd> and click <kbd>Open Anyway</kbd> at the bottom as shown in the following screenshot.

![macSecurity](images/macSecurity.png)
<div style="text-align:center"><i>The arrow indicates where you should click to open the app.</i></div>

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## Glossary

* **Academic level**: Mainstream academic years from primary to tertiary education in Singapore (i.e. Primary 1-6, Secondary 1-5, Junior College 1-2 and Year 1-6).
* **Academic stream**: Mainstream tracks in Singapore (i.e. Express, NA, NT, IP, IB) as well as other common exam streams (e.g. IELTS, SAT, ACT).
* **CLI**: Command Line Interface - a type of user interface through which users interact with the app in the form of text inputs only.
* **GUI**: Graphical User Interface - a type of user interface through which users interact with the app via visual representations.
* **JAR**: Java Archive - a file format used for aggregating multiple Java class files and their associated components (e.g. images) into a single file for distribution.
* **Lesson Rates**: Amount charged per hour for lessons.
* **Makeup lesson**: A lesson that occurs only once, for a student who has missed a previous lesson.
* **Mainstream OS**: Windows, Linux, Unix, OS-X.
* **Recurring lesson**: A lesson that will occur more than once in patterned intervals.
* **UI**: User Interface - the means by which the user and the app interact.
* **UTC+8**: The UTC offset used by Singapore Standard Time (SST), 8 hours ahead of UTC. Historically also referred to as GMT+8.
  UTC, or Coordinated Universal Time, is the primary time standard by which the world regulates clocks and time.
* **UX**: User Experience - The experience a user has when using the app.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## Command summary
This section lists all available commands in TAB, along with examples on how you can use them.


Action | Format, Examples
--------|------------------
**Help** | `help`
**Add Student** | `add n/NAME a/ADDRESS [p/PHONE_NUMBER] [e/EMAIL] [pp/PARENT_PHONE_NUMBER] [pe/PARENT_EMAIL] [sch/SCHOOL] [stream/ACAD_STREAM] [lvl/ACAD_LEVEL] [f/OUTSTANDING_FEES] [r/REMARKS] [t/TAG]…`<br><br> e.g. `add n/James Ho a/123, Clementi Rd, 1234665 p/22224444 e/jamesho@example.com pp/33335555 pe/danielho@example.com sch/DHS lvl/Y1 f/50 r/retainee t/cousin`
**Edit Student** | `edit INDEX [n/NAME] [a/ADDRESS] [p/PHONE] [e/EMAIL] [pp/PARENT_PHONE_NUMBER] [pe/PARENT_EMAIL] [sch/SCHOOL] [stream/ACAD_STREAM] [lvl/ACAD_LEVEL] [f/OUTSTANDING_FEES] [r/REMARK] [t/TAG]…`<br><br> e.g. `edit 2 n/James Lee e/jameslee@example.com`
**Delete Student** | `delete INDEX`<br><br> e.g. `delete 3`
**List Students** | `list`
**Find Students** | `find [cond/{all &#124; any &#124; none}] [n/NAME_KEYWORDS] [a/ADDRESS_KEYWORDS] [p/PHONE_KEYWORDS] [e/EMAIL_KEYWORDS] [pp/PARENT_PHONE_KEYWORDS] [pe/PARENT_EMAIL_KEYWORDS] [sch/SCHOOL_KEYWORDS] [stream/ACAD_STREAM_KEYWORDS] [lvl/ACAD_LEVEL_KEYWORDS] [t/TAG_KEYWORD]…​`
**View Tags** | `tag`
**Add Lesson** | `ladd INDEX [recurring/] date/dd MMM yyyy time/HHmm-HHmm subject/SUBJECT [hw/HOMEWORK]…​`<br><br> e.g. `ladd 1 recurring/ date/10 Nov 2021 time/1000-1200 subject/Math`
**Delete Lesson** | `ldelete INDEX LESSON_INDEX`<br><br> e.g.`ldelete 2 1`
**View Schedule** | `schedule`
**Clear** |`clear`
**Undo** | `undo`
**Redo** | `redo`
**Exit** | `exit`
