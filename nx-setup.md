# Nx Setup

## Create a new Nx workspace and run the init generator

```bash
npm init -y  # Create package.json if it doesn't exist
npx nx@latest init

> Minimum
> Skip for now
```

```bash
pnpm add -D @nx/devkit # add nx devkit as a dev dependency
npx nx generate @jnxplus/nx-maven:init

> ✔ What groupId would you like to use for root aggregator project? · com.avaloq.tools.ddk
> ✔ What name would you like to use for root aggregator project? · ddk-parent
> ✔ What version would you like to use for root aggregator project? · 16.1.0-SNAPSHOT
> ✔ Would you like to use Prettier for code formatting? · prettier
```
