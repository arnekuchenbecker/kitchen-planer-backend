## Server Aufbau

### Architektur
- Spring Boot
- Use spring boot stuff as required (Controllers, Services, Repositories, ...)

### Projekt Management
- Store entire projects, interactions with the server concerning one specific project should only happen in entire projects
- Project (and Recipe) Images should be stored and requested separately from projects (i.e. when requesting a project, one is sent the endpoint / URL at which they can find the image)
- Projects and Recipes used in those projects should be downloaded to the phone (including the relevant images)
#### Images
- Store as file (.jpg)
- Need to figure out how to transmit files via HTTP/HTTPS (we also need to do this on the App side)

### User Management
- Need to store which users are part of which project
- User authentication (see Android Support)

### Update Policy
- Store version numbers on server and in app
- App requests current project on opening a project or when the user clicks the update button

### Android Support
- Support Android Credential Manager for easy login on user side
- Support Android App Links


## App Questions
- Can I download recipes (which are not in a project)?
- Credential Manager Support
- Android App Link Support (i.e. have the links actually point to the correct domain)
