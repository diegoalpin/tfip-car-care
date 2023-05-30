import os

# class SetEnvironment():
    
def execute():
    os.environ["thisiskey"]="thisisthevalue"
    
def main():
    os.environ["RABBITMQ_HOST"]="18.136.81.222"
    os.environ["RABBITMQ_USERNAME"]="diego"
    os.environ["RABBITMQ_PASSWORD"]="hahaha"
    # os.environ["RABBITMQ_PORT"]='5672'
    os.environ["MYSQL_URL"]="jdbc:mysql://root:OrSx5C67jT085KfUjH21@containers-us-west-162.railway.app:7244/tfipcarcare?jdbcCompliantTruncation=false"
    os.environ["MYSQL_USERNAME"]="root"
    os.environ["MYSQL_PASSWORD"]="OrSx5C67jT085KfUjH21"
    # os.environ["REDISPORT"]="15945"
    os.environ["REDISHOST"]="redis-15945.c1.ap-southeast-1-1.ec2.cloud.redislabs.com"
    os.environ["REDISUSER"]="default"
    os.environ["REDISPASSWORD"]="Rxib3e4T2JtoqSRuQ6SR7VliGdiAoiOb"
    os.environ["STRIPE_SECRET"]="sk_test_51NCP7xGy7tyoR9JXnPGZimc8CA7nGZTIgXzpRISzZk2MtiGdQhKA3BVTQRJxYaroVdPQb4n7U1Gltrw3KkNbCQvf00Hk29qiKv"
    print(os.getenv("MYSQL_URL"))
        
if __name__ == "__main__":
    main()