#########################################
# RDS MySQL
#########################################

resource "aws_db_instance" "mysql" {

  identifier = "${var.project_name}-${var.environment}-mysql"

  #########################################
  # Engine
  #########################################

  engine         = "mysql"
  engine_version = var.engine_version

  instance_class = var.db_instance_class

  #########################################
  # Database
  #########################################

  db_name  = var.db_name
  username = var.db_username
  password = var.db_password

  #########################################
  # Storage
  #########################################

  allocated_storage     = var.allocated_storage
  max_allocated_storage = 100

  storage_type      = "gp3"
  storage_encrypted = true

  #########################################
  # Network
  #########################################

  db_subnet_group_name   = aws_db_subnet_group.main.name
  vpc_security_group_ids = [
    aws_security_group.mysql.id
  ]

  publicly_accessible = false

  #########################################
  # Backup
  #########################################

  backup_retention_period = 7
  backup_window           = "03:00-04:00"

  #########################################
  # Maintenance
  #########################################

  maintenance_window = "sun:04:00-sun:05:00"

  #########################################
  # Availability
  #########################################

  multi_az = false

  #########################################
  # Monitoring
  #########################################

  performance_insights_enabled = false

  monitoring_interval = 0

  #########################################
  # Snapshot
  #########################################

  skip_final_snapshot = true

  deletion_protection = false

  #########################################
  # Updates
  #########################################

  auto_minor_version_upgrade = true

  apply_immediately = true

  #########################################
  # Tags
  #########################################

  tags = merge(var.default_tags, {
    Name = "${var.project_name}-mysql"
  })
}