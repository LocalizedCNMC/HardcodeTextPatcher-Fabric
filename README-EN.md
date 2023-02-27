<center><div align="center">

<img height="150" src="icon/icon.png" width="150"/>

# Hardcode Patcher (WIP)

An unofficial Fabric Port of Vault Patcher. Let the hard coded strings change into localization strings in some mods.

[ZH-CN](README.md) / English

</div></center>

# Configs File

## Modular

the format in `config/HardcodePatcher/` directory like is `config.json` and several `module.json`. (Module files are currently required to exist in one or more of the Fabric versions)

`config.json` must be provided.
It is as follows:
```json
{
  "mods": [
    "module"
  ], 
  "debug_mode": {
    "is_enable": false,
    "output_mode": 0,
    "output_format": "<source> -> <target>"
  }
}
```

### Mods (Module files are currently required to exist in one or more of the Fabric versions)
`config.json` defined `module.json`.
Only in this way can `module.json` read and used normally.

### Debug Mode
**(Tips: ONLY DEBUG)**

`is_enable` determines whether the debugging feature is enabled.
If it is `true`, a line of debugging information will be output to the log when replacing the string,
The format of debugging information is `output_format`, and determines the content of debugging information by `output_format`.
If it is `false`, Will not output anything to the log when replacing the string.

`output_format` determines the format of debugging information. The default is `<source> -> <target>`.
`<source>` is the source string, that is, the string before replacement. and `<target>`is the replacement string.
There is another `<stack>` that is not shown, if you include it in `output_format`,
the log will be polluted because of the large amount of output information.
`<stack>` is a stacktrace array, which is the `StackTrace` (including this mod) of the class of this string,
This value is more for `stack_depth` and `name` in `target_class`.

`output_mode` determines the content of debugging information.
If it is 0, only the replaced string will be output,
If it is 1, only the strings that are not replaced are output.

## Module

The format of the Module File is roughly as follows:

```json
[
  {
    "target_class": {
      "name": "",
      "mapping": "Intermediaty",
      "stack_depth": -1
    },
    "key": "I'm key",
    "value": "@I'm value"
  },
  {
    "target_class": {
      "name": "me.modid.item.relics",
      "mapping": "SRG",
      "stack_depth": 3
    },
    "key": "Dragon Relic",
    "value": "namespace.modify.modid.item.relics.dragonrelic"
  },
  {
    "target_class": {
      "name": "",
      "mapping": "Intermediaty",
      "stack_depth": 0
    },
    "key": "Talents",
    "value": "namespace.modify.the_vault.gui.talnets"
  }
]
```
or
```json
[
  {
    "key": "I'm key",
    "value": "@I'm value"
  },
  {
    "key": "Dragon Relic",
    "value": "namespace.modify.modid.item.relics.dragonrelic"
  },
  {
    "key": "Talents",
    "value": "namespace.modify.the_vault.gui.talnets"
  }
]
```
.
Where

```json
{
  "target_class": {
    "name": "",
    "mapping": "Intermediaty",
    "stack_depth": -1
  },
  "key": "I'm key",
  "value": "@I'm value"
}
``` 

is a Translate Key Value Pair Objects, this object includes`key`, `value` and `target_class`.

### Key Value Pairs

#### Key
`key`, as the name implies, it refers to the string to be translated.

If you want to translate the `Copyright Mojang AB. Do not distribute!` in the title screen,
you can type `"key":"Copyright Mojang AB. Do not distribute!"`.

#### Value

With keys, there must be values.

So if you want to change `Copyright Mojang AB. Do not distribute!` to `Mojang AB.`.
you can type `"value":"Mojang AB."`.

#### Semi-match

All of the above methods are full match (that is, full replace), and only replace the same text as `key`.

If you want to semi-match, or the original string contains formatted text (such as `§a`, `%d`, `% s`, etc.),
you can try to add the `@` character before the string of `value` to achieve semi-matching.

For Example：
```json
{
  "key": "Grass",
  "value": "@GLASS"
}
```
This will replace all `GLASS` with `Grass` (such as `Grass Block`, `Grass`, `Tall Grass`, etc.)


This completes a basic key value pair.
If there is no mistake, it should be as follows:

```json
{
  "key": "Copyright Mojang AB. Do not distribute!",
  "value": "Mojang AB."
}
```

For Example:

```json
{
  "target_class": {
    "name": "",
    "mapping": "Intermediaty",
    "stack_depth": -1
  },
  "key": "Copyright Mojang AB. Do not distribute!",
  "value": "Mojang AB."
}
```

## Advanced Config

### Target Class

`target_class`, this object is mainly used to specify different `value` of two identical `key`.
Simple explanation：

Now there is a GUI with `Close` button (Refers to close the GUI), and there is another gui with `Close` button (Refers
to close the Pipe),

they have different meanings, but if not added `target_class`, then their translation content is the same.
So we need`target_class`.

`target_class` has three keys: `name`, `mapping` and `stack_depth`.

### Name

The matching rules of `name` are as follows:

#### Class Match

* The string starts with `#`, will be regarded as Class Match (For Example: `#TitleScreen` will
  match `net.minecraft.client.gui.screen.TitleScreen` 
  and `net.minecraft.client.gui.screen.titlescreen`.
  but will not match `net.minecraft.client.gui.titlescreen.screen`)

#### Package Match

* The string starts with `@`, will be regarded as Package Match (For Example: `#net.minecraft.client` will
  match `net.minecraft.client.gui.screen.TitleScreen`
  and `net.minecraft.client.gui.screen.DeathScreen`.
  Also match `net.minecraft.client.gui.titlescreen.screen`)

#### Full Match

* The string does not start with `#` or `@`, will be considered as a full match (For
  Example: `net.minecraft.client.gui.screen.TitleScreen` will match `net.minecraft.client.gui.screen.TitleScreen`
  and `net.minecraft.client.gui.screen.titlescreen`
  but will not match `net.minecraft.client.gui.titlescreen.screen`)

### Mapping

Reserved Field.

### Stack Depth

The stack depth is used for more accurate matching classes in the stack.
For example:

```
java.base/java.lang.Thread.getStackTrace(Thread.java:1610), 
TRANSFORMER/minecraft@1.18.2/net.minecraft.client.gui.screen.TitleScreen(TitleScreen.java:3),
...
```

In the example.
`stack_depth` of `net.minecraft.client.gui.screen.TitleScreen` is 2.
The size of `stack_depth` depends on the position of the stack to be located in the array,
Use `stack_depth`, `name` cannot be fuzzy match.

For Example：

```json
{
  "target_class": {
    "name": "net.minecraft.client.gui.screen.TitleScreen",
    "mapping": "Intermediaty",
    "stack_depth": 2
  },
  "key": "Copyright Mojang AB. Do not distribute!",
  "value": "Mojang AB."
}
```

Now, you can accurately locate the class `net.minecraft.client.gui.screen.TitleScreen`.

### Sample Config

**_（For Vault Hunter 3rd Edition）_**

```json
[
  {
    "target_class": {
      "name": "",
      "mapping": "Intermediaty",
      "stack_depth": 0
    },
    "key": "Attack Damage",
    "value": "namespace.modify.the_vault.gui.attackdamage"
  },
  {
    "target_class": {
      "name": "",
      "mapping": "Intermediaty",
      "stack_depth": 0
    },
    "key": "Dragon Relic",
    "value": "namespace.modify.the_vault.item.relics.dragonrelic"
  },
  {
    "target_class": {
      "name": "",
      "mapping": "Intermediaty",
      "stack_depth": 0
    },
    "key": "Talents",
    "value": "namespace.modify.the_vault.gui.talnets"
  }
]
```

If you look carefully, you will find that `target_class` key is rarely used in config.

## Others

#### Author of the Fabric port: TexTrue

#### Original development team:

- Original Author：[FengMing](https://github.com/3093FengMing)

- Configuration Author：[teddyxlandlee](https://github.com/teddyxlandlee)

- Idea：[yiqv](https://github.com/yiqv)

#### Original Mod Link：[github](https://github.com/3093FengMing/VaultPatcher)
