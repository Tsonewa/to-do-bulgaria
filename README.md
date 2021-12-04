<div id="top"></div>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://res.cloudinary.com/dabxnbbrp/image/upload/v1638639364/logo_uyjq4f.png">
    <img src="images/logo.jpg" alt="Logo" width="200" height="80">
  </a>

  <h3 align="center">To-Do Bulgaria</h3>

</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>


<!-- ABOUT THE PROJECT -->
## About The Project

[To-Do Bulgaria Screen Shot][product-screenshot](https://res.cloudinary.com/dabxnbbrp/image/upload/v1638638640/Screenshot_1_j7dcle.png)

To-Do Bulgaria is an travel application that enables users to quickly and easily explore a destination’s they have never been to by using other users trips information. Provides fully planed trips split in daily itineraries. It allows the users to exchange their traveling experience.

<p align="right">(<a href="#top">back to top</a>)</p>

### Built With

* [Spring Framework](https://spring.io/)
* [Spring Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
* [JUnit5](https://junit.org/junit5//)
* [Mockito](https://site.mockito.org/)
* [MySQL](https://www.mysql.com/)
* [H2DB](https://www.h2database.com/html/main.html)
* [Maven](https://maven.apache.org/guides/index.html)
* [SLF4j](http://www.slf4j.org/)
* [Cloudinary](https://cloudinary.com/)
* [OpenWeatherAPI](https://openweathermap.org/api)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

### Installation

1. Get a free API Key at [https://github.com/Tsonewa/to-do-bulgaria](https://github.com/Tsonewa/to-do-bulgaria)
2. Clone the repo
   ```sh
   git clone https://github.com/Tsonewa/to-do-bulgaria.git
   ```
3. Setup MySQL in `application.yml`
   ```yml
   datasource:
        password: YOUR_DS_PASSWORD
        username: YOUR_DS_USERNAME
   ```
4. Setup Cloudinary Properties in `application.yml`
   ```yml
    cloudinary:
     api-key: YOUR_API_KEY
     api-secret: YOUR_SECRET_KEY
     cloud-Name: YOUR_CLOUD_NAME
   ```
   5. Init data only once `application.yml`
    ```yml
     sql:
      init:
         mode: always
   ```

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

Dimitrinka Tsoneva - [@tsoneva](https://www.linkedin.com/in/dimitrinka-tsoneva-65650b105/) - tsonewa@gmail.com

Project Link: [https://github.com/Tsonewa/to-do-bulgaria](https://github.com/Tsonewa/to-do-bulgaria)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [Choose an Open Source License](https://choosealicense.com)
* [Font Awesome](https://fontawesome.com)
* [Google Fonts](https://fonts.google.com/)
* [OpenWeather](https://openweathermap.org/api)
* [ReadME Template](https://github.com/othneildrew/Best-README-Template)


<p align="right">(<a href="#top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/othneildrew
[product-screenshot]: images/screenshot.png