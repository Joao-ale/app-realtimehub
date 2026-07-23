-- Set timezone
SET TIME ZONE 'UTC';

-- Users table
CREATE TABLE users (
    id VARCHAR(255) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    profile_photo_url VARCHAR(1024),
    bio TEXT,
    status VARCHAR(20) DEFAULT 'OFFLINE' CHECK (status IN ('ONLINE', 'OFFLINE', 'AWAY', 'DND')),
    last_seen TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_status ON users(status);

-- Chats table (supports both 1-1 and group chats)
CREATE TABLE chats (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    chat_type VARCHAR(20) NOT NULL CHECK (chat_type IN ('PRIVATE', 'GROUP')),
    creator_id VARCHAR(255) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    group_photo_url VARCHAR(1024),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_chats_creator_id ON chats(creator_id);
CREATE INDEX idx_chats_type ON chats(chat_type);

-- Participants table (join table for users and chats)
CREATE TABLE participants (
    id VARCHAR(255) PRIMARY KEY,
    chat_id VARCHAR(255) NOT NULL REFERENCES chats(id) ON DELETE CASCADE,
    user_id VARCHAR(255) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role VARCHAR(20) NOT NULL DEFAULT 'MEMBER' CHECK (role IN ('OWNER', 'ADMIN', 'MEMBER')),
    joined_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    left_at TIMESTAMP WITH TIME ZONE,
    is_active BOOLEAN DEFAULT TRUE,
    muted_until TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX idx_participants_unique ON participants(chat_id, user_id) WHERE is_active = TRUE;
CREATE INDEX idx_participants_user_id ON participants(user_id);
CREATE INDEX idx_participants_chat_id ON participants(chat_id);

-- Messages table
CREATE TABLE messages (
    id VARCHAR(255) PRIMARY KEY,
    chat_id VARCHAR(255) NOT NULL REFERENCES chats(id) ON DELETE CASCADE,
    sender_id VARCHAR(255) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    content TEXT,
    message_type VARCHAR(20) DEFAULT 'TEXT' CHECK (message_type IN ('TEXT', 'IMAGE', 'FILE', 'AUDIO', 'VIDEO', 'EMOJI')),
    is_edited BOOLEAN DEFAULT FALSE,
    edited_at TIMESTAMP WITH TIME ZONE,
    is_deleted BOOLEAN DEFAULT FALSE,
    deleted_at TIMESTAMP WITH TIME ZONE,
    reply_to_id VARCHAR(255) REFERENCES messages(id) ON DELETE SET NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_messages_chat_id ON messages(chat_id);
CREATE INDEX idx_messages_sender_id ON messages(sender_id);
CREATE INDEX idx_messages_created_at ON messages(created_at);
CREATE INDEX idx_messages_reply_to ON messages(reply_to_id);

-- Message attachments table
CREATE TABLE message_attachments (
    id VARCHAR(255) PRIMARY KEY,
    message_id VARCHAR(255) NOT NULL REFERENCES messages(id) ON DELETE CASCADE,
    file_url VARCHAR(1024) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(50),
    file_size BIGINT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_attachments_message_id ON message_attachments(message_id);

-- Message reactions table
CREATE TABLE message_reactions (
    id VARCHAR(255) PRIMARY KEY,
    message_id VARCHAR(255) NOT NULL REFERENCES messages(id) ON DELETE CASCADE,
    user_id VARCHAR(255) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    emoji VARCHAR(10) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX idx_reactions_unique ON message_reactions(message_id, user_id, emoji);
CREATE INDEX idx_reactions_message_id ON message_reactions(message_id);

-- Notifications table
CREATE TABLE notifications (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    notification_type VARCHAR(50) NOT NULL,
    related_user_id VARCHAR(255) REFERENCES users(id) ON DELETE SET NULL,
    related_message_id VARCHAR(255) REFERENCES messages(id) ON DELETE SET NULL,
    related_chat_id VARCHAR(255) REFERENCES chats(id) ON DELETE SET NULL,
    is_read BOOLEAN DEFAULT FALSE,
    read_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_notifications_user_id ON notifications(user_id);
CREATE INDEX idx_notifications_is_read ON notifications(is_read);
CREATE INDEX idx_notifications_created_at ON notifications(created_at);

-- WebSocket sessions table (for tracking active connections)
CREATE TABLE websocket_sessions (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    session_id VARCHAR(255) UNIQUE NOT NULL,
    device_type VARCHAR(50),
    ip_address VARCHAR(45),
    user_agent TEXT,
    connected_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    disconnected_at TIMESTAMP WITH TIME ZONE,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE INDEX idx_websocket_sessions_user_id ON websocket_sessions(user_id);
CREATE INDEX idx_websocket_sessions_session_id ON websocket_sessions(session_id);
