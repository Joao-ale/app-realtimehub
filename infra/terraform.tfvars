########################################
# Projeto
########################################

project_name = "realtimehub"
environment  = "dev"

########################################
# AWS
########################################

aws_region        = "sa-east-1"
availability_zone = "sa-east-1a"

########################################
# Networking
########################################

vpc_cidr            = "10.0.0.0/16"
public_subnet_cidr  = "10.0.1.0/24"
private_subnet_cidr = "10.0.2.0/24"

########################################
# EC2
########################################

instance_type       = "t3.micro"
key_pair_name       = "realtimehub-key"
ec2_root_volume_size = 20

########################################
# Banco de Dados
########################################

db_name           = "realtimehub"
db_username       = "admin"
db_password       = "ALTERE_PARA_UMA_SENHA_FORTE"
db_instance_class = "db.t3.micro"
allocated_storage = 20
engine_version    = "8.0"

########################################
# Tags
########################################

default_tags = {
  Project     = "RealtimeHub"
  Environment = "Development"
  ManagedBy   = "Terraform"
  Owner       = "Joao Alexandre"
}