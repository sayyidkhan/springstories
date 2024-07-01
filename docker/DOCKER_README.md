### How to start the mariadb database

1. Navigate into the directory
    ```bash
    cd ./mariadb
    ```
1. Build the docker image by running the command below
    ```bash
    docker build -t springstories-mariadb .
    ```

2. Run the MariaDB Container: Once the image is built, you can run a container from it
    ```bash
    docker run -d -p 3306:3306 --name my-mariadb-container springstories-mariadb
    ```

> Now your mariadb server started successfully at port 3306

### How to check if the service is running

it will show all the services that are running in background

```bash
docker ps
```

example output
```agsl
CONTAINER ID   IMAGE                   COMMAND                  CREATED         STATUS          PORTS                              NAMES
852cdd6a4bcb   springstories-mariadb   docker-entrypoint.s   5 minutes ago   Up 4 minutes    0.0.0.0:3100->3100/tcp, 3306/tcp   mariadb-container
```


### how to stop the service from running

you can get the `CONTAINER ID` by running the command to find out
what are the services which are running in the background. (previous command above)

```agsl
docker stop <container_id>
```

### how to remove existing containers

you can remove containers not in use by running
```agsl
docker ps -a
```

example output
```agsl
CONTAINER ID   IMAGE                   COMMAND                  CREATED              STATUS                          PORTS                    NAMES
4d8d86c86373   springstories-mariadb   docker-entrypoint.s     About a minute ago   Exited (0) About a minute ago                            my-mariadb-container
852cdd6a4bcb   springstories-mariadb   docker-entrypoint.s     16 minutes ago       Exited (0) 5 minutes ago                                 mariadb-container
```

followed by 

```agsl
docker rm <container_id>
```