-- RealtimeHub initial schema
-- UUID primary keys, audit timestamps, indexes for query patterns

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Users
CREATE TABLE users (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name            VARCHAR(100) NOT NULL,
    email           VARCHAR(255) NOT NULL UNIQUE,
    password_hash   VARCHAR(255) NOT NULL,
    photo_url       VARCHAR(512),
    status          VARCHAR(20) NOT NULL DEFAULT 'OFFLINE',
    last_seen_at    TIMESTAMPTZ,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_status ON users (status);

-- Refresh tokens
CREATE TABLE refresh_tokens (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id         UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    token_hash      VARCHAR(255) NOT NULL UNIQUE,
    expires_at      TIMESTAMPTZ NOT NULL,
    revoked         BOOLEAN NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens (user_id);
CREATE INDEX idx_refresh_tokens_token_hash ON refresh_tokens (token_hash);

-- Chats
CREATE TABLE chats (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type            VARCHAR(20) NOT NULL,
    name            VARCHAR(255),
    created_by      UUID NOT NULL REFERENCES users(id),
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_chats_type ON chats (type);
CREATE INDEX idx_chats_created_by ON chats (created_by);

-- Participants
CREATE TABLE participants (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    chat_id         UUID NOT NULL REFERENCES chats(id) ON DELETE CASCADE,
    user_id         UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role            VARCHAR(20) NOT NULL DEFAULT 'MEMBER',
    joined_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE (chat_id, user_id)
);

CREATE INDEX idx_participants_chat_id ON participants (chat_id);
CREATE INDEX idx_participants_user_id ON participants (user_id);

-- Messages
CREATE TABLE messages (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    chat_id             UUID NOT NULL REFERENCES chats(id) ON DELETE CASCADE,
    sender_id           UUID NOT NULL REFERENCES users(id),
    type                VARCHAR(20) NOT NULL DEFAULT 'TEXT',
    content             TEXT,
    media_url           VARCHAR(512),
    reply_to_id         UUID REFERENCES messages(id),
    forwarded_from_id   UUID REFERENCES messages(id),
    status              VARCHAR(20) NOT NULL DEFAULT 'SENT',
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    edited_at           TIMESTAMPTZ,
    deleted_at          TIMESTAMPTZ
);

CREATE INDEX idx_messages_chat_id_created_at ON messages (chat_id, created_at DESC);
CREATE INDEX idx_messages_sender_id ON messages (sender_id);

-- Message reactions
CREATE TABLE message_reactions (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    message_id      UUID NOT NULL REFERENCES messages(id) ON DELETE CASCADE,
    user_id         UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    emoji           VARCHAR(32) NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE (message_id, user_id, emoji)
);

CREATE INDEX idx_message_reactions_message_id ON message_reactions (message_id);

-- Message read receipts
CREATE TABLE message_read_receipts (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    message_id      UUID NOT NULL REFERENCES messages(id) ON DELETE CASCADE,
    user_id         UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    read_at         TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE (message_id, user_id)
);

CREATE INDEX idx_message_read_receipts_message_id ON message_read_receipts (message_id);
CREATE INDEX idx_message_read_receipts_user_id ON message_read_receipts (user_id);

-- Notifications
CREATE TABLE notifications (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id         UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    type            VARCHAR(30) NOT NULL,
    title           VARCHAR(255) NOT NULL,
    body            TEXT NOT NULL,
    reference_id    UUID,
    status          VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    read_at         TIMESTAMPTZ
);

CREATE INDEX idx_notifications_user_id_status ON notifications (user_id, status);
CREATE INDEX idx_notifications_created_at ON notifications (created_at DESC);
