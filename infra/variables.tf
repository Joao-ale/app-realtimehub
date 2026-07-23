variable "project_name" {
  description = "Nome do projeto"
  type        = string
  default     = "realtimehub"
}

variable "environment" {
  description = "Ambiente da infraestrutura"
  type        = string
  default     = "dev"
}

variable "aws_region" {
  description = "Região da AWS"
  type        = string
  default     = "sa-east-1"
}

variable "availability_zone" {
  description = "Zona de disponibilidade"
  type        = string
  default     = "sa-east-1a"
}

#############################
# Networking
#############################

variable "vpc_cidr" {
  description = "CIDR da VPC"
  type        = string
  default     = "10.0.0.0/16"
}

variable "public_subnet_cidr" {
  description = "CIDR da subnet pública"
  type        = string
  default     = "10.0.1.0/24"
}

variable "private_subnet_cidr" {
  description = "CIDR da subnet privada"
  type        = string
  default     = "10.0.2.0/24"
}

#############################
# EC2
#############################

variable "instance_type" {
  description = "Tipo da instância EC2"
  type        = string
  default     = "t3.micro"
}

variable "key_pair_name" {
  description = "Nome da Key Pair existente na AWS"
  type        = string
}

variable "ec2_root_volume_size" {
  description = "Tamanho do disco da EC2 (GB)"
  type        = number
  default     = 20
}

#############################
# RDS MySQL
#############################

variable "db_name" {
  description = "Nome do banco de dados"
  type        = string
  default     = "realtimehub"
}

variable "db_username" {
  description = "Usuário administrador do banco"
  type        = string
  default     = "admin"
}

variable "db_password" {
  description = "Senha do banco"
  type        = string
  sensitive   = true
}

variable "db_instance_class" {
  description = "Classe da instância RDS"
  type        = string
  default     = "db.t3.micro"
}

variable "allocated_storage" {
  description = "Armazenamento do banco (GB)"
  type        = number
  default     = 20
}

variable "engine_version" {
  description = "Versão do MySQL"
  type        = string
  default     = "8.4"
}

#############################
# Tags
#############################

variable "default_tags" {
  description = "Tags padrão aplicadas a todos os recursos"
  type        = map(string)

  default = {
    Project     = "RealtimeHub"
    Environment = "Development"
    ManagedBy   = "Terraform"
    Owner       = "Joao Alexandre"
  }
}