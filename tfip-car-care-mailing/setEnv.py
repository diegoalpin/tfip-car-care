"""Run a command with configuration from a local JSON file; never prints values."""
import json
import os
from pathlib import Path
import subprocess
import sys


def main():
    if len(sys.argv) < 2:
        raise SystemExit("Usage: python setEnv.py <command> [args...] (see SECRETS.md)")
    config = Path(__file__).with_name("secrets.local.json")
    try:
        values = json.loads(config.read_text(encoding="utf-8"))
    except (OSError, ValueError):
        raise SystemExit("Create a valid secrets.local.json from secrets.example.json") from None
    if not isinstance(values, dict) or any(not isinstance(v, str) or not v.strip() for v in values.values()):
        raise SystemExit("Configuration must be an object containing nonempty string values")
    raise SystemExit(subprocess.call(sys.argv[1:], env={**os.environ, **values}))


if __name__ == "__main__":
    main()
