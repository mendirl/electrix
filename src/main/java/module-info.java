import org.jspecify.annotations.NullMarked;

@NullMarked
module electrix {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.boot.security;
    requires spring.context;
    requires spring.core;
    requires spring.integration.core;
    requires spring.integration.file;
    requires spring.modulith.api;
    requires spring.security.config;
    requires spring.security.web;
    requires org.jspecify;
    requires spring.messaging;
}
