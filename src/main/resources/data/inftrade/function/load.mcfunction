schedule function inftrade:load 2s replace


execute as @e[type=villager] if data entity @s Offers.Recipes[0] run data modify entity @s Offers.Recipes[] merge value {demand:-1,maxUses:2147483647}
execute as @e[type=wandering_trader] if data entity @s Offers.Recipes[0] run data modify entity @s Offers.Recipes[] merge value {demand:-1,maxUses:2147483647}