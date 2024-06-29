# TB-HIS: The Boys Handicap Index System

TB-HIS is a comprehensive golf handicap tracking system designed for "The Boys" Discord server.
It allows users to record, track, and compare their golf scores and handicaps through a seamless integration
with Discord. The system features a RESTful API for flexibility and scalability.

## Building and Running the Project Locally

To build and run the Link Vault application locally, follow these steps:

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/NoahH99/tb-his.git
   ```

2. Navigate to the project directory:

   ```bash
   cd tb-his
   ```

3. Copy the Docker Compose configuration file:

   ```bash
   cp docker-compose-example.yaml docker-compose.yaml
   ```

   Edit `docker-compose.yaml` with the necessary information for your setup.

4. Run Docker Compose to build and start the containers:

   ```bash
   docker-compose up --build
   ```

5. Once the containers are up and running, you can access the application at `http://localhost`.

## Contributing

Contributions to the TB-HIS application are welcome! If you find any issues or have suggestions for improvements, please
open an issue or submit a pull request.

# License

This project is licensed under the [MIT License](/LICENSE).

```
MIT License

Copyright (c) 2024 Noah Hendrickson

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
