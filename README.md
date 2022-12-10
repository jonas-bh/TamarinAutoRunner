# TamarinAutoRunner
Repo for our ITU Research Project for automating the testing of different threat combinations in Tamarin.

## Requirements

* Java
* [Tamarin Prover](https://tamarin-prover.github.io)

## Protocol setup
### Rules
TamarinAutoRunner requires the rules describing each threat to be annotated in the Tamarin protocol .spthy file using the following notation: 

```
#ifdef THREAT1
rule rule1 [color=#FF0000]:
...
#endif

#ifdef THREAT2
rule rule2 [color=#FF0000]:
...
#endif
```

It is possible to have multiple rules for one threat and these should be enclosed inside the same `#ifdef` and `#endif` pair in that case.

### Lemmas
One lemma should be defined per security property the modelled system should maintain. These are subsequently parsed to find the maximal threat combinations for each.

## Usage

Invoke TamarinAutoRunner from a command prompt using

`TamarinAutoRunner -pf <PROTOCOL_FILE> [OPTIONS]`

where

`PROTOCOL_FILE` refers to a Tamarin protocol .spthy file annotated as described in [Protocol Setup](#protocol-setup)

Supported options are:

`-of <ORACLE_FILE>` refers to an [oracle file](https://tamarin-prover.github.io/manual/book/011_advanced-features.html#using-an-oracle), if necessary for implementing custom proof goal heuristics in protocol

`-tam <TAMARIN_BIN>` refers to the bin folder of a Tamarin installation, necessary only if `tamarin-prover` is not configured as a system environment variable
