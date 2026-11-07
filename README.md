# SweepPaper 26.2.0 — feature-suite source

This is the initial SweepPaper feature-suite plugin project, designed for a Paper 26.2 server.
It is **not** a replacement server implementation or a compiled server JAR. The actual server
must remain the official Paper 26.2 server JAR; this project adds SweepPaper branding and
initial operational features as a plugin.

## Included first-release foundations
- SweepCore: plugin bootstrap, configuration defaults, `/sweep` command.
- SweepPerformance: average tick-time monitoring and configurable warning threshold.
- SweepChunks: chunk-load policy settings reserved for a later safe implementation.
- SweepGuard: explicit protection-policy settings; defaults do not block gameplay.
- SweepCommands: `/sweep status` and `/sweep reload`.
- SweepMonitor: periodic server health logging.
- SweepMOTD: branding prefix setting for future MOTD integration.
- SweepConfig: `config.yml` with configurable thresholds and toggles.

Some listed modules are foundations/configuration hooks, not full engine-level optimizations.
Do not claim they change Paper's internal chunk scheduler or server performance.

## Build
Requires JDK 25 and internet access:
`gradle build`

Output: `build/libs/SweepPaper-26.2.0.jar`

## Install
1. Back up the server.
2. Keep the official Paper 26.2 server JAR as the server executable.
3. Put the built SweepPaper plugin JAR in `plugins/`.
4. Start once, review logs, then adjust `plugins/SweepPaper/config.yml`.

## Compatibility
Target: Paper API `26.2.build.129-stable`, Java 25.
