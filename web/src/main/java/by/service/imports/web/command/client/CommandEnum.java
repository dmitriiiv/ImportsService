package by.service.imports.web.command.client;

import by.service.imports.web.command.ActionCommand;
import by.service.imports.web.command.AddEntriesCommand;
import by.service.imports.web.command.EntriesCommand;
import by.service.imports.web.command.MenuCommand;
import by.service.imports.web.command.PageCommand;
import by.service.imports.web.command.SuccessImportCommand;

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
	PAGE {
		@Override
		public ActionCommand getCurrentCommand() {
			return new PageCommand();
		}
	},
	SUCCESS_IMPORT {
		@Override
		public ActionCommand getCurrentCommand() {
			return new SuccessImportCommand();
		}
	};
	
	public abstract ActionCommand getCurrentCommand();
}
