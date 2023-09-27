# Voxelwrench
_Bring geometry nodes to your favorite voxel engine!_

## Initial goal
The goal of this project is to allows users to create a procedural structure generators by connecting nodes, similar to Blender Geometry Nodes.

Actually, you know what? The **REAL** initial goal (it's real I promise!) of this project is to help me create procedurally generated dungeon rooms faster. Imagine just putting dungeon room size and it generates an entire room with procedurally generated features, from random decorations to random puzzles.

## Use cases
- Fast structures construction.

## Supported voxel engines
- [MagicaVoxel](https://ephtracy.github.io/).
    - MagicaVoxel models generator is limited to 1 single model with maximum volume of 256x256x256. Extensions are not supported.
    - This was initially supported because I need to test something without waiting for Minecraft to start.

## Planned supports for voxel engines
- Minecraft
    - Fabric for both server and client (in progress)
    - Bukkit for server only (using plugin messaging channel)

## Some Q&A so you don't have to go ask me
Q: Voxelwrench for Fabric on older version of Minecraft?

A: It is likely that this project will only go towards latest version of Minecraft. If you want older version, consider cloning this repository and revert to specific commit. No it doesn't support anything older than 1.20.2.

That said if you know modding, and the version that you want to backport supports Java 17 then you can go ahead and "port" Voxelwrench mod. After all, Voxelwrench Core is not bounds to specific Minecraft version. Heck it even supports MagicaVoxel!

---

Q: Voxelwrench for Forge? Voxelwrench for Bukkit?

A: Voxelwrench for Bukkit is planned, but Voxelwrench for Forge is not, at least not officially. Voxelwrench Core, however, can be used to make Forge mod if you want.

## Copyright and license
(c) nahkd123. Voxelwrench is licensed under MIT license. See [LICENSE](./LICENSE) for more info.

The license **permits everyone** to fork this repository and port to whatever platform they wants **without asking**, assuming you keep the license notice.

If you are a madman and have ported Voxelwrench to Forge 1.12.2 or **lower**, please hit me up in [Issues tab](https://github.com/nahkd123/voxelwrench/issues) so I can see how low a 1.12.2 Forge modder can get :tiny_potato:.