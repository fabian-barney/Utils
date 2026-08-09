# utils-java

`utils-java` is a small Java utility library for converting storage and transfer
units across byte- and bit-based scales.

It includes:

- `ByteUnit` for byte units such as `BYTE`, `KIB`, `MIB`, `KB`, and `MB`
- `BitUnit` for bit units such as `BIT`, `KIBIT`, `MIBIT`, `KBIT`, and `MBIT`
- cross-conversion helpers between byte and bit units
- overloads that accept a custom bits-per-byte value instead of assuming `Byte.SIZE`

## Requirements

- Java 21 minimum
- PR CI verifies compatibility with Java 21 and Java 25
- Maven wrapper included in the repository

## Build

On Unix-like systems:

```bash
./mvnw -B -ntp verify
```

On Windows:

```powershell
.\mvnw.cmd -B -ntp verify
```

`verify` runs the library tests plus the shared `crap-java` and
`cognitive-java` gates.

To run only the `crap-java` quality gate plugin while still executing the full
Maven `verify` lifecycle on Unix-like systems:

```bash
./mvnw -B -ntp -P!quality-gates-all,quality-gate-crap verify
```

On Windows:

```powershell
.\mvnw.cmd -B -ntp -P!quality-gates-all,quality-gate-crap verify
```

To run only the `cognitive-java` quality gate plugin while still executing the
full Maven `verify` lifecycle on Unix-like systems:

```bash
./mvnw -B -ntp -P!quality-gates-all,quality-gate-cognitive verify
```

On Windows:

```powershell
.\mvnw.cmd -B -ntp -P!quality-gates-all,quality-gate-cognitive verify
```

To run the static nullness analysis locally, enable the NullAway profile:

```bash
./mvnw -Pnullaway test
```

```powershell
.\mvnw.cmd -Pnullaway test
```

## Usage

```java
import media.barney.utils.unit.BitUnit;
import media.barney.utils.unit.ByteUnit;

double kibibytes = ByteUnit.MB.toKiB(5);
double megabits = ByteUnit.MIB.toMbit(2);
double bytes = BitUnit.MBIT.toBytes(100);
double gibibytes = BitUnit.GBIT.toGiB(64);
```

You can also convert values from one unit enum into another directly:

```java
double mib = ByteUnit.MIB.convert(1536, ByteUnit.KIB);
double mbit = BitUnit.MBIT.convert(12, ByteUnit.MB);
```

If you need a custom bits-per-byte value, use the overloads that accept an `int`:

```java
double bytes = BitUnit.KIBIT.toBytes(8, 16);
double bits = ByteUnit.KIB.toBits(4, 16);
```

## Supported Units

`ByteUnit` supports:

- Binary units: `BYTE`, `KIB`, `MIB`, `GIB`, `TIB`, `PIB`
- Decimal units: `KB`, `MB`, `GB`, `TB`, `PB`

`BitUnit` supports:

- Binary units: `BIT`, `KIBIT`, `MIBIT`, `GIBIT`, `TIBIT`, `PIBIT`
- Decimal units: `KBIT`, `MBIT`, `GBIT`, `TBIT`, `PBIT`

## Notes

- Conversions return `double`.
- Byte-to-bit conversions assume 8 bits per byte by default.
- The conversion helpers include overflow guards for multiplication-heavy paths.

## Release

See `CHANGELOG.md` for release history and `RELEASING.md` for the Maven Central
workflow, required GitHub secrets, and rollback expectations.

## License

Apache License 2.0. See `LICENSE`.
