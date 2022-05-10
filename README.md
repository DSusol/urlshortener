[![Java CI with Gradle](https://github.com/DSusol/urlshortener/actions/workflows/gradle.yml/badge.svg?branch=repositories)](https://github.com/DSusol/urlshortener/actions/workflows/gradle.yml)
[![codecov](https://codecov.io/gh/DSusol/urlshortener/branch/repositories/graph/badge.svg?token=Y8UQ4IKSEE)](https://codecov.io/gh/DSusol/urlshortener)

Test bot:
http://t.me/simple_url_shortener_test_bot

Production bot:
http://t.me/simple_url_shortener_bot

### **Local development hints:**
- use dev profile 
- run docker container using docker-compose.yml file
- make sure BOT_NAME & BOT_TOKEN env variable are set for test bot version
- database credentials must be consistent between _docker-compose.yml_ and _application-dev.properties_ files.