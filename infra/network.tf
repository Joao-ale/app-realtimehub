#########################################
# VPC
#########################################

resource "aws_vpc" "main" {
  cidr_block           = var.vpc_cidr
  enable_dns_support   = true
  enable_dns_hostnames = true

  tags = merge(var.default_tags, {
    Name = "${var.project_name}-vpc"
  })
}

#########################################
# Internet Gateway
#########################################

resource "aws_internet_gateway" "main" {
  vpc_id = aws_vpc.main.id

  tags = merge(var.default_tags, {
    Name = "${var.project_name}-igw"
  })
}

#########################################
# Public Subnet A
#########################################

resource "aws_subnet" "public_a" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = var.public_subnet_cidr
  availability_zone       = var.availability_zone
  map_public_ip_on_launch = true

  tags = merge(var.default_tags, {
    Name = "${var.project_name}-public-a"
    Tier = "Public"
  })
}

#########################################
# Public Subnet B
#########################################

resource "aws_subnet" "public_b" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = var.public_subnet_cidr_b
  availability_zone       = var.availability_zone_b
  map_public_ip_on_launch = true

  tags = merge(var.default_tags, {
    Name = "${var.project_name}-public-b"
    Tier = "Public"
  })
}

#########################################
# Private Subnet A
#########################################

resource "aws_subnet" "private_a" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = var.private_subnet_cidr
  availability_zone = var.availability_zone

  tags = merge(var.default_tags, {
    Name = "${var.project_name}-private-a"
    Tier = "Private"
  })
}

#########################################
# Private Subnet B
#########################################

resource "aws_subnet" "private_b" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = var.private_subnet_cidr_b
  availability_zone = var.availability_zone_b

  tags = merge(var.default_tags, {
    Name = "${var.project_name}-private-b"
    Tier = "Private"
  })
}

#########################################
# Public Route Table
#########################################

resource "aws_route_table" "public" {
  vpc_id = aws_vpc.main.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.main.id
  }

  tags = merge(var.default_tags, {
    Name = "${var.project_name}-public-rt"
  })
}

#########################################
# Route Table Associations
#########################################

resource "aws_route_table_association" "public_a" {
  subnet_id      = aws_subnet.public_a.id
  route_table_id = aws_route_table.public.id
}

resource "aws_route_table_association" "public_b" {
  subnet_id      = aws_subnet.public_b.id
  route_table_id = aws_route_table.public.id
}

#########################################
# DB Subnet Group
#########################################

resource "aws_db_subnet_group" "main" {
  name = "${var.project_name}-db-subnet-group"

  subnet_ids = [
    aws_subnet.private_a.id,
    aws_subnet.private_b.id
  ]

  tags = merge(var.default_tags, {
    Name = "${var.project_name}-db-subnet-group"
  })
}