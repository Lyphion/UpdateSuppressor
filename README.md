# UpdateSuppressor

## General Information

This plugin enables the suppression of nearly all block updates in predefined regions.

## Usage

The regions where the block updates are suppressed can be modified with the `suppressor` command, with requires the `suppressor.admin` permission (or _OP_).
The central command for this plugin is `suppressor`. For an overview of all available subcommand use `suppressor help`.

New regions can be created with `suppressor add <x> <y> <z> <x> <y> <z>`. 
To modify an existing region step into it and use `suppressor edit <x> <y> <z> <x> <y> <z>` to change to bound.
A region can be removed via `suppressor remove`, while standing in the region.
The `suppressor toggle` command can be used to show the region outlines/bounds.
This plugin allows for the modification of the region file (`regions.yml`) without a server restart. To reload the configurations use `suppressor reload`.
