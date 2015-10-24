package by.service.imports.web.command.client;

import by.service.imports.web.command.ActionCommand;
import by.service.imports.web.command.AddEntriesCommand;
import by.service.imports.web.command.EntriesCommand;
import by.service.imports.web.command.ImportCommand;
import by.service.imports.web.command.MenuCommand;
import by.service.imports.web.command.PageCommand;

public enum CommandEnum {
	MENU {
		@Override
		public ActionCommand getCurrentCommand() {
			return new MenuCommand();
		}
	},
	ENTRIES	{
		@Override
		public ActionCommand getCurrentCommand() {
			return new EntriesCommand();
		}
	}, 
	ADD_ENTRIES {
		@Override
		public ActionCommand getCurrentCommand() {
			return new AddEntriesCommand();
		}
	},
	IMPORT_FILE {
		@Override
		public ActionCommand getCurrentCommand() {
			return new ImportCommand();
		}
	}, 
	PAGE {
		@Override
		public ActionCommand getCurrentCommand() {
			return new PageCommand();
		}
	};
	
	public abstract ActionCommand getCurrentCommand();
}
