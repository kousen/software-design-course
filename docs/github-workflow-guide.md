# GitHub Workflow Guide for CPSC 310

This guide explains how to work with GitHub in this course. It covers both the **assignment submission workflow** (fork-and-submit) and the **professional development workflow** (issue-based feature development) that we demonstrate in class.

## Prerequisites

Before starting, ensure you have:
1. A GitHub account (free at [GitHub.com](https://github.com))
2. Git installed on your computer ([git-scm.com](https://git-scm.com))
3. IntelliJ IDEA configured with Git

## Part 1: Assignment Submission Workflow

This is the workflow you'll use for individual assignments.

### Step 1: Fork the Assignment Repository

1. Navigate to the assignment repository link provided in Moodle
2. Click the **Fork** button in the top-right corner
3. Select your personal GitHub account as the destination
4. Wait for the fork to complete (usually takes a few seconds)

### Step 2: Clone Your Fork Locally

1. On your forked repository page, click the green **Code** button
2. Copy the HTTPS URL (should look like `https://github.com/YOUR-USERNAME/assignment-name.git`)
3. Open IntelliJ IDEA
4. Select **File → New → Project from Version Control**
5. Paste the URL and choose a local directory
6. Click **Clone**

Alternative: Using the command line
```bash
cd ~/Documents/CPSC310  # Or your preferred directory
git clone https://github.com/YOUR-USERNAME/assignment-name.git
cd assignment-name
```

### Step 2.5: Add Upstream Remote (Important!)

After cloning your fork, add the original repository as an upstream remote:

```bash
git remote add upstream https://github.com/ORIGINAL-REPO/assignment-name.git
```

This allows you to pull any updates from the original assignment if needed:
```bash
git pull upstream main
```

Verify your remotes are set correctly:
```bash
git remote -v
# Should show:
# origin    https://github.com/YOUR-USERNAME/assignment-name.git (fetch)
# origin    https://github.com/YOUR-USERNAME/assignment-name.git (push)
# upstream  https://github.com/ORIGINAL-REPO/assignment-name.git (fetch)
# upstream  https://github.com/ORIGINAL-REPO/assignment-name.git (push)
```

### Step 3: Complete the Assignment

1. Open the project in IntelliJ IDEA
2. Read the assignment README for instructions
3. Implement the required functionality
4. Run tests frequently with `./mvnw test` or IntelliJ's test runner
5. Commit your changes regularly:
   ```bash
   git add .
   git commit -m "Implement feature X"
   ```

### Step 4: Push Your Changes

After completing the assignment:

```bash
git push origin main
```

Or in IntelliJ:
1. **VCS → Commit** (Ctrl/Cmd + K)
2. Write a commit message
3. Click **Commit and Push**

### Step 5: Submit to Moodle

1. Go to your forked repository on GitHub
2. Copy the repository URL from your browser's address bar
   - It should look like: `https://github.com/YOUR-USERNAME/assignment-name`
3. Go to the assignment in Moodle
4. Paste your repository URL in the submission text field
5. Click **Submit**

## Important Notes

### Do NOT Create Pull Requests
- Unlike typical open-source workflows, do NOT create a pull request back to the original repository
- Your fork should remain separate for grading purposes

### Keep Your Repository Public
- Ensure your forked repository remains **public** so instructors can access it
- Do not delete your fork until after grades are posted

### Collaboration Policy
If working with a partner:
1. One person forks and adds the other as a collaborator
2. Go to **Settings → Manage access → Add people**
3. Both students submit the same repository URL in Moodle
4. Include both names in the README or a CONTRIBUTORS file

### Making Updates After Submission
If you need to make changes after submitting:
1. Push new commits to your fork
2. No need to resubmit in Moodle (we'll grade the latest version)
3. Notify the instructor if pushing significant changes after the deadline

## Troubleshooting

### "Permission denied" when pushing
- Make sure you're pushing to YOUR fork, not the original repository
- Check the remote URL: `git remote -v`
- Your remote should point to `github.com/YOUR-USERNAME/...`

### Can't find the Fork button
- Make sure you're logged into GitHub
- You may have already forked it—check your repositories

### Lost local changes
- Before making changes, always check you're on the right branch: `git branch`
- Commit frequently to avoid losing work
- Use `git status` to see uncommitted changes

### Merge conflicts
- These shouldn't occur if you're working in your own fork
- If they do, ask for help during office hours

## Getting Help

- **Git issues:** See the instructor during office hours
- **Assignment questions:** Post in the course Moodle forum
- **GitHub problems:** Check [GitHub's documentation](https://docs.github.com)

## Quick Command Reference

| Action              | Command                   |
|---------------------|---------------------------|
| Clone repository    | `git clone <url>`         |
| Check status        | `git status`              |
| Add all changes     | `git add .`               |
| Commit changes      | `git commit -m "message"` |
| Push to GitHub      | `git push origin main`    |
| Pull latest changes | `git pull origin main`    |
| Add upstream remote | `git remote add upstream <original-repo-url>` |
| Pull from upstream  | `git pull upstream main`  |
| See commit history  | `git log --oneline`       |

Remember: The key is to fork first, work in your fork, and submit your fork's URL to Moodle!

---

## Part 2: Professional Development Workflow

This is the workflow used for contributing to the main course repository and professional software development. We demonstrate this process when adding new course materials.

### Overview: Issue-Based Feature Development

1. **Create GitHub Issue** → 2. **Create Feature Branch** → 3. **Implement with TDD** → 4. **Create Pull Request** → 5. **Code Review & Merge** → 6. **Cleanup**

### Step 1: Create a GitHub Issue

Before implementing any feature, create an issue to track the work:

```bash
gh issue create --title "Feature Title" --body "Detailed description of what needs to be implemented"
```

Or manually on GitHub:
1. Go to the repository
2. Click **Issues** tab
3. Click **New Issue**
4. Write a clear title and detailed description
5. Include acceptance criteria and requirements

### Step 2: Create a Feature Branch

Create a dedicated branch for your work:

```bash
# Make sure you're on main and it's up to date
git checkout main
git pull origin main

# Create and switch to a new feature branch
git checkout -b feature/issue-description
```

**Branch naming conventions:**
- `feature/add-solid-examples`
- `feature/dry-principle-implementation`
- `bugfix/fix-validation-error`
- `docs/update-setup-guide`

### Step 3: Implement with Test-Driven Development (TDD)

Follow the TDD cycle while implementing:

1. **Red**: Write a failing test
2. **Green**: Write minimal code to make it pass
3. **Refactor**: Improve the code while keeping tests green

```bash
# Run tests frequently
./gradlew test

# Commit regularly with meaningful messages
git add .
git commit -m "Add validation tests for email format"
git commit -m "Implement email validation logic"
git commit -m "Refactor validation to eliminate duplication"
```

### Step 4: Create a Pull Request

Once your feature is complete and all tests pass:

```bash
# Push your feature branch to GitHub
git push -u origin feature/your-feature-name

# Create a pull request using GitHub CLI
gh pr create --title "Implement Feature X" --body "Description of changes"
```

**Pull Request Best Practices:**
- Write a clear title and description
- Reference the issue: "Closes #123"
- Include test results
- Explain what was implemented
- Add screenshots for UI changes

### Step 5: Code Review and Merge

After creating the PR:
1. **Code Review**: Team members review the changes
2. **Address Feedback**: Make requested changes if needed
3. **Merge**: Once approved, merge the PR
4. **Close Issue**: GitHub automatically closes linked issues

```bash
# Merge and cleanup (done by reviewer/maintainer)
gh pr merge 123 --squash --delete-branch
```

### Step 6: Cleanup and Sync

After the PR is merged:

```bash
# Switch back to main
git checkout main

# Pull the latest changes (including your merged feature)
git pull origin main

# Your feature branch is automatically deleted on GitHub
# Delete it locally too
git branch -d feature/your-feature-name
```

### Benefits of This Workflow

- **Traceability**: Every change is linked to an issue
- **Code Quality**: TDD ensures comprehensive testing
- **Collaboration**: Pull requests enable code review
- **Documentation**: Issues and PRs provide project history
- **Safety**: Feature branches prevent breaking main branch

### Example: Adding DRY Principle Examples

Here's how we implemented DRY examples using this workflow:

1. **Issue #2**: "Implement DRY (Don't Repeat Yourself) Principle Examples"
2. **Branch**: `feature/dry-principle-examples`
3. **TDD**: Wrote tests first, then implementation
4. **PR #3**: Comprehensive description with test results
5. **Merge**: Squashed commits and merged to main
6. **Cleanup**: Feature branch automatically deleted

### When to Use Each Workflow

**Assignment Workflow (Fork-and-Submit):**
- Individual assignments
- Student submissions
- When you need a separate copy for grading

**Professional Workflow (Issue-Branch-PR):**
- Contributing to shared repositories
- Team projects
- Adding features to the course materials
- Professional software development

---