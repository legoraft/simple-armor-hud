# Dev docs

The main armor hud is built from the [armorHudMixin](../src/client/java/com/armorhud/mixin/client/armorHudMixin.java). It calls the different functions in the [hud](../src/client/java/com/armorhud/hud/) section. The main y-axis adjustments are in the [armorPlacement](../src/client/java/com/armorhud/hud/armorPlacement.java), while the x-axis is in [armorRenderer](../src/client/java/com/armorhud/hud/armorRenderer.java).

## Armor accessors

The armor accessors are available in the [armor](../src/client/java/com/armorhud/armor/) package. You can add a custom `ArmorAccessor`, which chould be named `<type>ArmorAccessor`. Add it to the combined ArmorAccessor, add it to the `armorList`. You can load it in the [armorHud.java](../src/client/java/com/armorhud/armorHud.java) file with the `armorAccessor.addAccessor()` function.

## Dev environment

I personally use [IntelliJ IDEA](https://www.jetbrains.com/idea/) as an IDE. Add the minecraft plugin and everything should work as expected. When committing to the repo, try to use the `scope: message` commit style wherever possible, falling back to [conventional commits](https://www.conventionalcommits.org/) when there isn't an option.
