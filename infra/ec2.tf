#########################################
# Ubuntu 24.04 LTS AMI
#########################################

data "aws_ami" "ubuntu" {
  most_recent = true

  owners = ["099720109477"] # Canonical

  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd-gp3/ubuntu-noble-24.04-amd64-server-*"]
  }

  filter {
    name   = "virtualization-type"
    values = ["hvm"]
  }

  filter {
    name   = "architecture"
    values = ["x86_64"]
  }
}

#########################################
# EC2 Instance
#########################################

resource "aws_instance" "realtimehub" {
  ami                         = data.aws_ami.ubuntu.id
  instance_type               = var.instance_type
  subnet_id                   = aws_subnet.public.id
  vpc_security_group_ids      = [aws_security_group.ec2.id]
  key_name                    = var.key_pair_name
  iam_instance_profile        = aws_iam_instance_profile.ec2.name
  associate_public_ip_address = true

  user_data = file("${path.module}/user_data.sh")

  root_block_device {
    volume_size           = var.ec2_root_volume_size
    volume_type           = "gp3"
    delete_on_termination = true
    encrypted             = true
  }

  metadata_options {
    http_endpoint = "enabled"
    http_tokens   = "required"
  }

  monitoring = false

  tags = merge(var.default_tags, {
    Name = "${var.project_name}-ec2"
  })
}

#########################################
# Elastic IP (Opcional)
#########################################

resource "aws_eip" "realtimehub" {
  domain = "vpc"

  instance = aws_instance.realtimehub.id

  tags = merge(var.default_tags, {
    Name = "${var.project_name}-eip"
  })
}