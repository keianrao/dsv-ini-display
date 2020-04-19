This program takes a DSV file as input and outputs an INI file.

The program needs the DSV's schema to populate the INI file fields - section names and keys. Therefore, it will ask for it in the arguments.

Tentative usage scenario:

> $ ls
> dsv-ini
> 
> $ cat > input
> #name:value:colour:priority
> grain:8:blue:4
> grain:6:green:3
> beans:10:red:4
> grain:4:purple:10
> 
> $ cat input | ./dsv-ini colour name value colour priority
> [blue]
> name=grain
> value=8
> priority=4
> 
> [green]
> name=grain
> value=6
> priority=3
> 
> [red]
> name=beans
> value=10
> priority=4
> 
> [purple]
> name=grain
> value=4
> priority=10

It's not a very useful program (particularly for me as I don't use INI files..), but I write it specifically to practice program testing.

Note: Hopefully I do not neglect it...
