# Voxelwrench protocol for Minecraft
## Overview
- Voxelwrench protocol over plugin messaging channel.
- Support Bukkit and Fabric or anything that can handle plugin message channel.

## Data types
> **Note:** All data types will be in **big endian** (because Java).

### Primitives
- `uintN`: Unsigned N-bit integer.
- `sintN`: Signed N-bit integer with the first MSB (Most significant bit) for sign.
- `type[integer or field]`: A continuous sequence of `type`, repeated for N times.
- `template` (Struct only): Template in structs.

### Complex
#### String
```
struct String {
    uint16 len;
    return uint8[len] content as java.lang.String;
}
```

## Message structure
```
struct Message {
    uint8 id; // Message ID. See below.
    template data; // Message data. See below.
}
```

## `0x01`: Fill from nodes