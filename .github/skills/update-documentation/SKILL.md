---
name: update-documentation
description: Update the project documentation in readme.md to reflect the current implementation. Use this when asked to update or refresh the project documentation.
---

# Skill: Update Documentation

Update the project documentation in `readme.md` to reflect the current implementation.

## Instructions

Follow these steps in order:

### Step 1: Analyze the codebase

Analyze the following parts of the codebase and compare them against the current `readme.md`:

- **pom.xml** (root and all modules) — Java/Kotlin version, dependencies, plugins, module list
- **Flyway migrations** in `eux-nav-rinasak-persistence/src/main/resources/db/migration/` — database tables, columns, types, constraints, and relationships
- **Entity classes** in `eux-nav-rinasak-model/src/main/kotlin/` — domain models and their fields
- **OpenAPI spec** in `eux-nav-rinasak-openapi/src/main/resources/static/` — API endpoints, request/response models
- **Controllers** in `eux-nav-rinasak-webapp/src/main/kotlin/` — REST endpoint implementations
- **Services** in `eux-nav-rinasak-service/src/main/kotlin/` — business logic
- **application.yml** in `eux-nav-rinasak-webapp/src/main/resources/` — runtime configuration
- **Dockerfile** and **Makefile** — build and deployment setup

### Step 2: Identify improvements

Compare the analysis from step 1 with the current `readme.md` content. Identify any discrepancies or missing information. Focus on:

- Incorrect or outdated technology versions (e.g. JDK version, Spring Boot version)
- Missing or renamed modules
- Database schema changes — tables, columns, types, constraints, or relationships that differ from the ER diagram in `readme.md`
- API endpoint changes
- Missing or incorrect build/run instructions
- Any other factual inaccuracies

### Step 3: Apply improvements

If there are improvements to make:

1. Create a new branch from `main`:
   ```
   git checkout main && git pull && git checkout -b docs/update-readme
   ```

2. Edit `readme.md` with all identified improvements. Follow these rules:
   - Keep the documentation concise and factual.
   - Maintain the existing structure and style of the document.
   - There must be exactly one diagram: the Mermaid ER diagram for the database schema.
   - The ER diagram must accurately reflect all Flyway migrations (tables, columns, types, constraints, relationships).
   - Do not add verbose explanations — keep it in line with the current tone.

3. Commit the changes:
   ```
   git add readme.md && git commit -m "docs: update readme to match current implementation"
   ```

4. Push the branch:
   ```
   git push -u origin docs/update-readme
   ```

5. Create a pull request using the `gh` CLI:
   ```
   gh pr create --title "docs: update readme to match current implementation" --body "Automated documentation update to align readme.md with the current codebase." --base main
   ```

If there are no improvements needed, report that the documentation is already up to date. Do not create a branch or pull request.
