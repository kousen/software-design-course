# GitHub Workflow Guide for CPSC 310

This guide explains how to work with GitHub for assignments in this course. You'll fork assignment repositories, complete the work in your fork, and submit the repository URL via Moodle.

## Prerequisites

Before starting, ensure you have:
1. A GitHub account (free at [GitHub.com](https://github.com))
2. Git installed on your computer ([git-scm.com](https://git-scm.com))
3. IntelliJ IDEA configured with Git

## Assignment Workflow

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