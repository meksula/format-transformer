- Run RabbitMQ before you start this poc app

$ docker run --rm -it -d --hostname my-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management