<a id="readme-top"></a>


<div style="background-color:rgba(0, 0, 0, 0.0470588); text-align:center; vertical-align: middle; padding:40px 0;">
<h1 align="center"> Kulinaria 

[![Static Badge](https://img.shields.io/badge/version-1.1-blue.svg)](https://github.com/maciej-mazur-github/kulinaria)
[![Live Demo](https://img.shields.io/badge/demo-online-green.svg)](https://kulinaria.codingproductions.com/)

</h1> 

<h3 align="center">Technologies used:</h3>

<h1 align="center">

[![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/pl/java/technologies/downloads/#java21)
[![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)](https://hibernate.org/)
[![SQL](https://img.shields.io/badge/SQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://dev.mysql.com/doc/refman/8.4/en/language-structure.html)
[![H2_Database](https://img.shields.io/badge/H2_Database-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://www.h2database.com/html/main.html)
[![Thymeleaf][thymeleaf-link]](https://www.thymeleaf.org/)
[![HTML](https://img.shields.io/badge/HTML-239120?style=for-the-badge&logo=html5&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/HTML)
[![CSS](https://img.shields.io/badge/CSS-239120?&style=for-the-badge&logo=css3&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/CSS)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)](https://getbootstrap.com/)
[![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)](https://git-scm.com/)
[![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![Debian](https://img.shields.io/badge/Debian-A81D33?style=for-the-badge&logo=debian&logoColor=white)](https://www.debian.org/)
[![Bash](https://img.shields.io/badge/Shell_Script-121011?style=for-the-badge&logo=gnu-bash&logoColor=white)](https://www.gnu.org/software/bash/)
</h1> 

<p align="center">
  <a href="https://kulinaria.codingproductions.com/" target="_blank">
    <img alt="CheeseIcon" title="Live demo at https://www.kulinaria.codingproductions.com" src="src/main/resources/static/img/favicon.png" width="150">
  </a>
</p>

<h3 align="center">
  Hibernate and H2 database oriented Cookbook project
</h3>

<h2 align="center">
  <!-- <a href="https://www.kulinaria.codingproductions.com">
    <h1>View Live Demo</h1>
    <img alt="arrow_gif" src="https://i.pinimg.com/originals/ea/f4/5d/eaf45d79005980c820d3412d346d51dc.gif" height="100">
  </a> -->
    <a href="https://www.kulinaria.codingproductions.com" target="_blank"><h1>View Live Demo</h1></a> 
    <br> 
    <a href="https://www.kulinaria.codingproductions.com" target="_blank"><img alt="arrow_gif" src="images/github_readme/arrows.gif" height="100"></a> 
</h2>


</div>



<br><br>

## Table Of Content
<ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#solutions-worth-checking-out">Solutions Worth Checking out</a>
      <ul>
        <li><a href="#sql-scripts-creation">SQL Scripts Creation</a></li>
        <li><a href="#generating-project-links">Generating Project Links</a></li>
        <li><a href="#pagination">Pagination</a></li>
        <li><a href="#filtering-and-sorting">Filtering And Sorting</a></li>
        <li><a href="#use-of-photo-files-in-docker-container">Use Of Photo Files In Docker Container</a></li>
      </ul>
    </li>
    <li>
      <a href="#project-expansion-plans">Project Expansion Plans</a>
      <ul>
        <li><a href="#implementing-spring-security">Implementing Spring Security</a></li>
        <li><a href="#implementing-api">Implementing API</a></li>
        <li><a href="#filtering-recipes-by-name">Filtering recipes by name</a></li>
        <li><a href="#unit-testing">Unit testing</a></li>
        <li><a href="#responsive-design-for-smartphones">Responsive design for smartphones</a></li>
      </ul>
    </li>
  </ol>

<br><br><br>
## About The Project

<div style="background-color:rgba(0, 0, 0, 0.0470588); text-align:center; vertical-align: middle; padding:40px 0;">
<p align="center">
  <img alt="Kulinaria_screenhot" title="Kulinaria screenhot" src="images/github_readme/homepage_screenshot.png" width="1200">
</p>
</div>


This is a training Java application built for the purpose of myself evolving in the area of full stack programming focused on Java related technologies.<br><br> It is a Cookbook, still in the process of adding many more features in the near future. <br>
The main goal was to build a full stack web application that would extensively use Hibernate and H2 Database mechanisms, further deployed on the public Linux server (VPS, Debian).<br>
There have been several technologies combined to achieve the current result, some of which have been listed at the top of this document.
<br><br>
At this point project's UI is held in Polish language as a part of simulating challenges related to development of the project contracted by a non-English speaking client. 

<br><br>

<a id="action-list"></a>In this application you can:

- view the list of either all or categorized (dinners, salads, pasta, sandwiches) cooking recipes
- view the details of the chosen recipe (time of preparation, category, type, vote count, ingredients, preparation steps, photo)
- filter the recipes by type (meat or vegetarian) or clear the filter at any time
- sort recipes by title, vote number or preparation time (ascending and descending)
- use pagination to navigate through filtered and/or sorted recipe list
- edit each and every one of above mentioned details
- delete any recipe from database
- add a new recipe to the database


<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

### Built With

| Technology / Language     | Description |
| ----------- | ----------- |
|[![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot) | Spring boot 3.3.3, Spring Data JPA, Spring MVC|
|[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/pl/java/technologies/downloads/#java21)|Oracle JDK 21.0.3|
|[![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)](https://hibernate.org/)|For automatic table creation and test data upload|
| [![SQL](https://img.shields.io/badge/SQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://dev.mysql.com/doc/refman/8.4/en/language-structure.html) | Used along with Hibernate to load test data to tables |
|[![H2_Database](https://img.shields.io/badge/H2_Database-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://www.h2database.com/html/main.html) | Used as a convenient replacement for external database like MySQL. At this point H2 Database fully meets project's requirements |
|[![Thymeleaf][thymeleaf-link]](https://www.thymeleaf.org/)| Used as part of Spring MVC to build dynamic server web pages|
|[![HTML](https://img.shields.io/badge/HTML-239120?style=for-the-badge&logo=html5&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/HTML) [![CSS](https://img.shields.io/badge/CSS-239120?&style=for-the-badge&logo=css3&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/CSS) | Pretty extensive use of HTML 5 and CSS 3 capabilities |
|[![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)](https://getbootstrap.com/) |Version 4.3.1, used especially for styling and resizing recipe ingredients and preparation steps tables|
|[![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)](https://git-scm.com/) | Github used as a remote repository both for my local OS (Windows) repository and for VPS OS (Debian) repository |
|[![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/) |Installed on remote VPS. Dockerfile used for multi-stage build (Maven image to build JAR file and JDK image to run the actual application container). Also Nginx Proxy Manager container used for port forwarding and for HTTPS SSL |
[![Debian](https://img.shields.io/badge/Debian-A81D33?style=for-the-badge&logo=debian&logoColor=white)](https://www.debian.org/) | Debian GNU/Linux 12 (bookworm) used as a Virtual Private Server (VPS) |
|[![Bash](https://img.shields.io/badge/Shell_Script-121011?style=for-the-badge&logo=gnu-bash&logoColor=white)](https://www.gnu.org/software/bash/) |Bash script for automation of application container full restart (image rebuild, container stoppage, removal and restart based on newly built image)|

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Solutions Worth Checking Out

### SQL Scripts Creation

The goal was to automate the creation of SQL scripts that would further be used to load test data to all database tables. The additional target was to use the pre-existing plain text containing all recipe ingredients, amounts and preparation descriptions. Hence any online SQL generator had to be omitted. <br><br>Creating all SQL DML queries manually would be an incredibly tedious task to do, therefore I decided to implement a separate project class (com.kulinaria.utils.TestDataCreator) with its own main function to use it once only and to let it create all the necessary SQL files with all the necessary DML queries in them for me automatically. <br><br>This allowed me to transform the following .txt files:

<p align="center">
  <img alt="txt_files_graphic" title="Txt files" src="images/github_readme/txt_files.png" width="300">
</p>
<p align="center">
    <a href=""></a>
  <img alt="txt_file_fragment" title="Txt file content fragment" src="images/github_readme/txt_example_content.png" width="800">
</p>

<br><br>...into these full-fledged SQL scripts further used by Hibernate / Spring Data JPA to load all the expected test data to the tables:
<p align="center">
  <img alt="sql_files_graphic" title="SQL files" src="images/github_readme/sql_files.png" width="200">
</p>
<p align="center">
  <img alt="sql_file_fragment" title="SQL file content fragment" src="images/github_readme/sql_content_fragment.png" width="900">
</p>
<br><br>
<p align="center">
  <img alt="h2_fragment" title="H2 fragment" src="images/github_readme/h2_fragment.png" width="900">
</p>

<br><br>To achieve such a goal I used a pretty interesting file operations and RegEx mechanisms, such as:

<p align="center">
  <img alt="regex_extraction_graphic" title="RegEx extraction" src="images/github_readme/regex_content_extraction.png" width="700">
</p>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Generating Project Links

As a typical database web project there is lots of different links needed here to easily navigate through the content and their actual form may frequently depend on the current context, like the currently viewed recipe, current sort order and/or the filter in use, number of the page etc.<br> Therefore I created a separate class (com.kulinaria.utils.LinkCreator) to handle this problem. There is a significant amount of methods that came in handy.

<p align="center">
  <img alt="link_creator_fragment" title="Link creator fragment" src="images/github_readme/link_creator_fragment.png" width="600">
</p>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Pagination

To implement pagination I made use of Pageable interface implemented by PageRequest class and of JpaSpecificationExecutor interface extended by my RecipeRepository (Spring Data Repository). You can track the details of the implementation in the class com.kulinaria.recipe.RecipeService, especially in the following method:

<p align="center">
  <img alt="use_of_pageable" title="Use of Pageable interface" src="images/github_readme/use_of_pageable.png" width="800">
</p>

Also I found an effective way to make use of Thymeleaf and Bootstrap to properly and dynamically implement the graphics related to the current pagination status (based on total number of pages and the number of current page to be viewed):

<p align="center">
  <img alt="thymeleaf_pagination" title="Thymeleaf pagination" src="images/github_readme/thymeleaf_pagination.png" width="600">
</p>

<p align="center">
    <img alt="pagination_graphic" title="Pagination graphic" src="images/github_readme/pagination_graphic.png" width="400">
</p>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Filtering And Sorting

To achieve retrieving from the database the recipes that are filtered, sorted and also paged at the same time I decided to use the Specification interface along with Criteria API. It allowed me to always query the database taking into account all the current filtering, sorting and paging parameters.<br>
I also made sure to make the application "remember" all these parameters whenever the user enters the recipe details view and returns back to the recently viewed category. Hence the user does not need to apply all these parameters again.<br>

<p align="center">
  <img alt="use_of_pageable" title="Use of Pageable interface" src="images/github_readme/use_of_pageable.png" width="800">
</p>
<p align="center">
  <img alt="use_of_pageable" title="Use of Pageable interface" src="images/github_readme/use_of_specification.png" width="800">
</p>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Use Of Photo Files In Docker Container

The live demo application is running on a remote Virtual Private Server (VPS) with Debian installed. There is also a Docker container (Debian bookworm with Open JDK 21) running on the native Debian. The image which the container originates from is multi-staged built based off of an image of Debian bookworm with Maven (you can track this in the Dockerfile). Maven produces JAR file that is further used to produce JDK image, that is later on run as as the actual application container. Therefore to maintain the possibility of adding new recipes in the application (along with recipe photo files) I had to use a different approach to photo files management than I did in the local 'dev' Spring profile. I decided not to include test data recipes photo files in the JAR file produced by Maven. Instead I copy all the photo files from the native Debian OS to JDK container each time it is restarted. The Bash shell script (kulinaria-build-and-restart.sh) handles this process:

<p align="center">
  <img alt="docker_image_files" title="Docker image files" src="images/github_readme/docker_image_files.png" width="800">
</p>

This allows the application to operate on the photo files in the conainer outside of the JAR file, hence it is also possible for the user to add as many new photo files as needed.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Project Expansion Plans

### Implementing Spring Security

At this point the application is lacking verification and authorization mechanisms. Therefore no user is asked for access credentials and anyone is capable of performing all the [actions mentioned earlier](#action-list). This is where Spring Security will come in handy which I will implement in the near future, just as I have already successfully done in another one of my projects (https://github.com/maciej-mazur-github/ofertownia.git). Users will be authorized to browse recipe lists and details. Administrators will be allowed to promote plain users to the role of administrators, add, edit or remove recipes from the database. Probably there will also be a role of super administrator who will have the power to degrade administrators back to the role of plain user and to remove administrators and users from the database.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Implementing API

I think it would be a good idea for the application to provide all the recipes in the form of REST API, just like I have done it in the project https://github.com/maciej-mazur-github/ofertownia.git.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Unit Testing

Unit tests still need to be implemented.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


### Filtering recipes by name

As of yet the application is lacking the proper name filtering function that would allow the user to find a desired recipe by its full or partial name. This option still needs to be implemented.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


### Responsive design for smartphones

Currently the application front end is projected nicely on tablets and all other wider screen devices. Projecting it on the smartphones still needs to be taken care of by designing and implementing a different page elements distribution.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->

[thymeleaf-link]: https://img.shields.io/badge/Thymeleaf-005f0f?style=for-the-badge&logoColor=white&logo=data:image/svg%2bxml;base64,PHN2ZyB2ZXJzaW9uPSIxLjAiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIKICAgICB3aWR0aD0iMTAyMS4wMDAwMDBwdCIgaGVpZ2h0PSIxMDIzLjAwMDAwMHB0IiB2aWV3Qm94PSIwIDAgMTAyMS4wMDAwMDAgMTAyMy4wMDAwMDAiCiAgICAgcHJlc2VydmVBc3BlY3RSYXRpbz0ieE1pZFlNaWQgbWVldCI+CiAgICA8cmVjdCB4PSIwIiB5PSIwIiB3aWR0aD0iMTAyMSIgaGVpZ2h0PSIxMDIzIiByeD0iODAiIGZpbGw9IndoaXRlIi8+CiAgICA8ZyB0cmFuc2Zvcm09InRyYW5zbGF0ZSgwLjAwMDAwMCwxMDIzLjAwMDAwMCkgc2NhbGUoMC4xMDAwMDAsLTAuMTAwMDAwKSIKICAgICAgIGZpbGw9IiMwMDVGMEYiIHN0cm9rZT0ibm9uZSI+CiAgICAgICAgPHBhdGggZD0iTTU5NSAxMDIxNSBjLTE0NCAtMjYgLTI3MiAtOTQgLTM3OSAtMjAxIC03NiAtNzYgLTEyMyAtMTQ3IC0xNjMKIC0yNDYgLTU2IC0xMzkgLTUzIDE1MCAtNTEgLTQ2OTMgMyAtNDIzMyA0IC00NDczIDIxIC00NTMwIDc2IC0yNjAgMjcwIC00NTIKNTI3IC01MjIgNjIgLTE3IDI4NCAtMTggNDU1NSAtMTggNDI1MCAwIDQ0OTMgMSA0NTU1IDE4IDI1OCA2OSA0NTYgMjY3IDUyNwo1MjcgMTcgNjIgMTggMjg1IDE4IDQ1NjUgMCA0MjgwIC0xIDQ1MDMgLTE4IDQ1NjUgLTcwIDI1NyAtMjYyIDQ1MSAtNTIyIDUyNwogLTU3IDE3IC0yOTUgMTggLTQ1MzAgMjAgLTM1OTEgMSAtNDQ4NCAtMSAtNDU0MCAtMTJ6IG03OTgzIC0xNjMyIGMtMTU5IC0zMjcKIC0yNzcgLTU4MCAtNjMxIC0xMzQ4IC01MDcgLTEwOTkgLTcyNyAtMTU0NiAtMTAzMCAtMjA5MCAtMjcxIC00ODYgLTYwNSAtMTAwMwogLTkxNyAtMTQyMCAtNTA2IC02NzUgLTEwMTAgLTEwODMgLTE1ODQgLTEyODIgLTQwMyAtMTM5IC04NTkgLTExNSAtMTI0MSA2NwogLTE3MyA4MyAtMzkwIDI1MCAtNDYwIDM1NiAtMTI2IDE4OSAtMTI2IDQ3MCAtMiA3MTkgMzYgNzMgOTYgMTQ3IDIzMCAyODQgMjMwCjIzNCA0MTcgMzg1IDg1MiA2ODYgODk3IDYyMiAxMDQ3IDcyOCAxNDIzIDEwMDkgODk3IDY3MCAxNjE4IDEyOTAgMjMzMiAyMDA1CjQwOCA0MDkgNzQwIDc3MSAxMDQyIDExMzYgNTcgNjkgMTA0IDEyNSAxMDUgMTI1IDEgMCAtNTMgLTExMSAtMTE5IC0yNDd6Cm0tNzQ5IC00NDUgYy0zMDkgLTMzMiAtODI0IC04MzQgLTEyMjkgLTExOTggLTUzOCAtNDg0IC0xMjUyIC0xMDUyIC0xOTAwCiAtMTUxMCAtOTYgLTY4IC0yNTggLTE4MyAtMzYwIC0yNTUgLTEwMiAtNzIgLTI4OCAtMjAzIC00MTUgLTI5MCAtNDg5IC0zMzYKIC03MDIgLTQ5NiAtOTE5IC02ODYgLTE2MCAtMTQwIC0zNjYgLTM1NCAtNDE1IC00MjkgLTc3IC0xMTggLTEzMiAtMjg0IC0xNDYKIC00MzQgbC04IC04MCAtMzMgNjUgYy04MSAxNTcgLTE1MyAzODQgLTE4NiA1ODQgLTcgNDcgLTEzIDE2NCAtMTIgMjgwIDAgMTY3CjMgMjE5IDIyIDMxMyA2MyAzMTYgMjE5IDU5NCA0NDUgNzk1IDI2MCAyMzEgNTgzIDQwNiAxMTIyIDYwNyAxMzE1IDQ5MCAyMzc4CjEwNjMgMzUyNSAxODk4IDIwNiAxNTAgNTk3IDQ0OSA2MDYgNDYzIDMgNSAxMCA5IDE2IDkgNiAwIC00NSAtNjAgLTExMyAtMTMyeiIvPgogICAgPC9nPgo8L3N2Zz4K
