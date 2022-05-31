package com.learning.urlshortener.bot.commands;

/**
 * List of telegram bot executable commands.
 * <p></p>
 * <b>Global</b> commands are the informing commands:
 * <p><i>/start</i> - shows welcome message</p>
 * <p><i>/help</i> - provides list of main commands</p>
 * <p></p>
 * <b>Main</b> commands are used to perform link operations:
 * <p><i>/*_link</i> - link operation command</p>
 * <p></p>
 * DEFAULT is used as a marker for MultiStepCommandHandler.
 */
public enum Command {
    DEFAULT,
    START,
    HELP,
    NEW_LINK,
    SHOW_LINK,
    MY_LINKS,
    DELETE_LINK
}
