#########################################
# Security Group - EC2
#########################################

resource "aws_security_group" "ec2" {
  name        = "${var.project_name}-ec2-sg"
  description = "Security Group da EC2"
  vpc_id      = aws_vpc.main.id

  #######################################
  # SSH
  #######################################

  ingress {
    description = "SSH"

    from_port = 22
    to_port   = 22
    protocol  = "tcp"

    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }

  #######################################
  # HTTP
  #######################################

  ingress {
    description = "HTTP"

    from_port = 80
    to_port   = 80
    protocol  = "tcp"

    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }

  #######################################
  # HTTPS
  #######################################

  ingress {
    description = "HTTPS"

    from_port = 443
    to_port   = 443
    protocol  = "tcp"

    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }

  #######################################
  # Spring Boot
  #######################################

  ingress {
    description = "Spring Boot"

    from_port = 8080
    to_port   = 8080
    protocol  = "tcp"

    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }

  #######################################
  # Redis (somente para testes)
  #######################################

  ingress {
    description = "Redis"

    from_port = 6379
    to_port   = 6379
    protocol  = "tcp"

    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }

  #######################################
  # Saída
  #######################################

  egress {
    description = "Internet"

    from_port = 0
    to_port   = 0
    protocol  = "-1"

    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }

  tags = merge(var.default_tags, {
    Name = "${var.project_name}-ec2-sg"
  })
}

#########################################
# Security Group - MySQL
#########################################

resource "aws_security_group" "mysql" {
  name        = "${var.project_name}-mysql-sg"
  description = "Security Group do MySQL"
  vpc_id      = aws_vpc.main.id

  ingress {
    description = "MySQL"

    from_port = 3306
    to_port   = 3306
    protocol  = "tcp"

    security_groups = [
      aws_security_group.ec2.id
    ]
  }

  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"

    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }

  tags = merge(var.default_tags, {
    Name = "${var.project_name}-mysql-sg"
  })
}