import os

# class SetEnvironment():
    
def execute():
    os.environ["thisiskey"]="thisisthevalue"
    
def main():
    os.environ["RABBITMQ_HOST"]="18.136.81.222"
    os.environ["RABBITMQ_USERNAME"]="receiver"
    os.environ["RABBITMQ_PASSWORD"]="hahaha"
    os.environ["RABBITMQ_PORT"]='5672'
    os.environ["GMAIL_USERNAME"]="tfipcarcare.2023"
    os.environ["GMAIL_PASSWORD"]="zapdqipfzyraejfo"
    print(os.getenv("RABBITMQ_PASSWORD"))
        
if __name__ == "__main__":
    main()