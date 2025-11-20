---
theme: seriph
background: https://source.unsplash.com/1920x1080/?artificial-intelligence,technology
class: text-center
highlighter: shiki
lineNumbers: false
info: |
  ## Week 12: Spring AI Integration

  CPSC 310: Software Design
  Trinity College, Fall 2025

  Kenneth Kousen
drawings:
  persist: false
transition: slide-left
title: "Spring AI: Building AI-Powered Applications"
mdc: true
---

# Spring AI: Building AI-Powered Applications

## ChatClient API & LLM Integration

<div class="pt-12">
  <span @click="$slidev.nav.next" class="px-2 py-1 rounded cursor-pointer" hover="bg-white bg-opacity-10">
    Enterprise AI with Spring Boot <carbon:arrow-right class="inline"/>
  </span>
</div>

<div class="abs-br m-6 flex gap-2">
  <span class="text-sm opacity-50">CPSC 310 | Fall 2025</span>
</div>

---
layout: two-cols
---

# This Week's Plan

<v-clicks>

## Session 22 (Today)
- What is Spring AI?
- ChatClient API fundamentals
- Configuring LLM providers
- Practical examples

## Session 23 (Thursday)
- Assignment 6 walkthrough
- Prompt engineering
- Error handling strategies
- Security considerations

</v-clicks>

::right::

<div class="mt-12">
<v-clicks>

### Key Concepts
- AI model abstraction
- Fluent API design
- Streaming responses
- Provider configuration

### What You'll Build
- AI-powered game players
- LLM decision-making
- Multi-provider integration
- Robust error handling

</v-clicks>
</div>

---

# What is Spring AI?

**Spring AI** provides Spring-friendly abstractions for building AI applications

<v-clicks>

## Key Features

- **Unified API** across multiple LLM providers
- **ChatClient** fluent API for interactions
- **Streaming** support for real-time responses
- **Spring Boot** auto-configuration
- **Production-ready** error handling

## Why Spring AI?

- Consistent API regardless of provider
- Easy to switch between models
- Familiar Spring patterns

</v-clicks>

---
background: https://cover.sli.dev
---

# The Spring AI Architecture

```mermaid
flowchart TD
    A[Your Application] --> B[ChatClient API]
    B --> C[Spring AI Core]
    C --> D[OpenAI]
    C --> E[Anthropic]
    C --> F[Ollama]
    C --> G[Other Providers]

    style B fill:#90EE90,stroke:#333
    style C fill:#87CEEB,stroke:#333
```

**Benefits:** Write once, run anywhere - switch providers via config

---

# Getting Started: Dependencies

Add Spring AI to your project

```kotlin
dependencies {
    implementation 'org.springframework.ai:spring-ai-openai-spring-boot-starter'
    implementation 'org.springframework.ai:spring-ai-anthropic-spring-boot-starter'
}
```

<v-clicks>

## Dependency Management

```kotlin
dependencyManagement {
    imports {
        mavenBom "org.springframework.ai:spring-ai-bom:1.1.0"
    }
}
```

**Note:** Spring AI 1.1.0 was released last week (Nov 2025)

</v-clicks>

---

# Configuration: OpenAI

Set up OpenAI in application.yml

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4o
          temperature: 0.7
          max-tokens: 2048
```

<v-clicks>

## Environment Variables

**Never commit API keys!**

```bash
export OPENAI_API_KEY=sk-...
```

Or set in IntelliJ Run Configuration

</v-clicks>

---

# Configuration: Anthropic

Set up Claude in application.yml

```yaml
spring:
  ai:
    anthropic:
      api-key: ${ANTHROPIC_API_KEY}
      chat:
        options:
          model: claude-sonnet-4-5
          temperature: 0.7
          max-tokens: 4096
```

<v-clicks>

## Model Names

- OpenAI: `gpt-4o`, `gpt-4o-mini`
- Anthropic: `claude-sonnet-4-5`, `claude-opus-4-5`
- Temperature: 0.0 (deterministic) to 1.0 (creative)

</v-clicks>

---

# The ChatClient API

Spring AI's fluent interface for AI interactions

```java
@RestController
class MyController {
    private final ChatClient chatClient;

    MyController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/ai")
    String chat(String input) {
        return chatClient.prompt()
            .user(input)
            .call()
            .content();
    }
}
```

**Fluent API:** `prompt() -> user() -> call() -> content()`

---

# ChatClient: Basic Usage

The simplest possible example

```java
String response = chatClient.prompt()
    .user("Tell me a joke")
    .call()
    .content();

System.out.println(response);
```

<v-clicks>

## Method Chain
1. `prompt()` - Start building
2. `user()` - Add user message
3. `call()` - Execute
4. `content()` - Extract response

</v-clicks>

---

# ChatClient Flow

<div class="flex justify-center">
<div class="w-2/3">

```mermaid
sequenceDiagram
    participant App
    participant CC as ChatClient
    participant AI as LLM

    App->>CC: prompt().user()
    CC->>AI: HTTP POST
    AI-->>CC: JSON
    CC-->>App: String
```

</div>
</div>

---

# ChatClient: Full Response

Access metadata and usage information

```java
ChatResponse response = chatClient.prompt()
    .user("What is the capital of France?")
    .call()
    .chatResponse();

String content = response.getResult()
    .getOutput().getContent();

Usage usage = response.getMetadata().getUsage();
```

<v-click>

**Usage includes:** Token counts and costs

</v-click>

---

# Streaming Responses

Get responses in real-time

```java
Flux<String> stream = chatClient.prompt()
    .user("Explain quantum computing")
    .stream()
    .content();

stream.doOnNext(chunk -> System.out.print(chunk))
    .doOnComplete(() -> System.out.println("\n[Done]"))
    .blockLast();
```

<v-clicks>

## Why Streaming?

- **Real-time feedback** - Show progress to users
- **Better UX** - Don't wait for entire response
- **Large responses** - Process as data arrives
- **Reactive** - Returns `Flux<String>` (Project Reactor)

</v-clicks>

---

# Multiple ChatClients

Configure different models

```java
@Configuration
class ChatClientConfig {

    @Bean
    ChatClient openAiChatClient(OpenAiChatModel model) {
        return ChatClient.builder(model).build();
    }

    @Bean
    ChatClient anthropicChatClient(AnthropicChatModel model) {
        return ChatClient.builder(model).build();
    }
}
```

Bean names match injection qualifiers

---

# Using Multiple Clients

Inject by name

```java
@Service
class GameService {
    private final ChatClient openAiClient;
    private final ChatClient anthropicClient;

    GameService(@Qualifier("openAiChatClient") ChatClient openAi,
                @Qualifier("anthropicChatClient") ChatClient anthropic) {
        this.openAiClient = openAi;
        this.anthropicClient = anthropic;
    }

    String getDecision(String prompt) {
        return openAiClient.prompt().user(prompt).call().content();
    }
}
```

---

# Prompt Engineering Basics

Effective prompts produce better results

```java
String prompt = """
    You are a tactical advisor in an RPG game.

    Situation: Your HP 50/100, Enemy HP 75/100

    Actions: 1) Attack (~30 dmg) 2) Heal (+30 HP)

    JSON: {"action": "attack|heal", "reasoning": "..."}
    """;

String response = chatClient.prompt()
    .user(prompt).call().content();
```

**Good prompts:** Role, context, format, guidance

---

# Structured Outputs with .entity()

ChatClient can parse JSON responses automatically

```java
record Decision(String action, String target, String reasoning) {}

Decision decision = chatClient.prompt()
    .user("Choose an action: attack or heal")
    .call()
    .entity(Decision.class);
```

<v-clicks>

## How It Works
- LLM returns JSON matching your record structure
- Spring AI handles parsing automatically
- No manual ObjectMapper needed
- Type-safe and clean

</v-clicks>

---

# Using .entity() with Complex Types

Works with nested structures

```java
record GameAction(
    String action,
    String target,
    String reasoning,
    int priority
) {}

GameAction action = chatClient.prompt()
    .user(buildGamePrompt())
    .call()
    .entity(GameAction.class);
```

<v-click>

**Spring AI automatically:**
- Requests JSON output from LLM
- Validates structure
- Handles type conversion

</v-click>

---

# Decision Record with Validation

Use Jackson annotations for required fields

```java
public record Decision(
    @JsonProperty(required = true) String action,
    @JsonProperty(required = true) String target,
    @JsonProperty String reasoning
) {}
```

<v-clicks>

## Benefits
- Type-safe LLM responses
- Automatic validation
- Clear contract with LLM
- Works with .entity() method

</v-clicks>

---

# Error Handling

Always have a fallback

```java
String getAiDecision(String prompt) {
    try {
        return chatClient.prompt()
            .user(prompt).call().content();
    } catch (Exception e) {
        return fallbackDecision();
    }
}
```

**Errors:** Network, rate limits, invalid keys

---

# Error Handling Flow

```mermaid
flowchart LR
    A[Call LLM] --> B{Success?}
    B -->|Yes| C[Return Response]
    B -->|No| D[Log Error]
    D --> E[Use Fallback]
    E --> F[Continue Game]

    style D fill:#ffcccc
    style E fill:#90EE90
```

---

# Assignment 6: AI Game Players

<v-clicks>

## Three Player Types

1. **HumanPlayer** - Console input
2. **RuleBasedPlayer** - If-then logic
3. **LLMPlayer** - ChatClient decisions

**Strategy Pattern:** All implement `Player` interface

</v-clicks>

---

# Assignment 6: Architecture

<div class="flex justify-center">
<div class="w-3/4">

```mermaid
classDiagram
    class Player {
        <<interface>>
        +decideAction()
    }
    class HumanPlayer
    class RuleBasedPlayer
    class LLMPlayer {
        -ChatClient
        +buildPrompt()
    }

    Player <|.. HumanPlayer
    Player <|.. RuleBasedPlayer
    Player <|.. LLMPlayer
    LLMPlayer --> ChatClient
```

</div>
</div>

**Strategy + Adapter patterns in action**

---

# Assignment 6: TODO 1

Build the LLM prompt

```java
String buildPrompt(Character self, List<Character> allies,
                   List<Character> enemies, GameState state) {
    return """
        You are %s, a %s with %d/%d HP.
        YOUR TEAM: %s
        ENEMIES: %s
        Choose: attack <enemy> or heal <ally>
        JSON: {"action": "...", "target": "...", "reasoning": "..."}
        """.formatted(self.getName(), self.getType(),
            self.getHealth(), self.getMaxHealth(),
            formatCharacterList(allies),
            formatCharacterList(enemies));
}
```

---

# Assignment 6: TODO 2

Call the LLM using .entity()

```java
GameCommand decideAction(Character self, List<Character> allies,
                        List<Character> enemies, GameState state) {
    String prompt = buildPrompt(self, allies, enemies, state);

    try {
        Decision decision = chatClient.prompt()
            .user(prompt)
            .call()
            .entity(Decision.class);

        // TODO 3: Convert Decision to GameCommand...
    } catch (Exception e) {
        return defaultAction(self, enemies);
    }
}
```

---

# Assignment 6: TODO 3

Convert Decision to GameCommand

```java
// decision is already a Decision object from TODO 2
Character target = decision.action().equals("attack")
    ? findCharacterByName(decision.target(), enemies)
    : findCharacterByName(decision.target(), allies);

return switch (decision.action().toLowerCase()) {
    case "attack" -> new AttackCommand(self, target);
    case "heal" -> new HealCommand(target, 30);
    default -> {
        System.out.println("Unknown action: " + decision.action());
        yield defaultAction(self, enemies);
    }
};
```

<v-click>

## Adapter Pattern

`LLMPlayer` adapts LLM JSON to `GameCommand` objects

</v-click>

---

# Assignment 6: Team Configuration

Set up both LLM providers

```java
// Team 1: Human + AI
Character warrior = CharacterFactory.createWarrior("Conan");
Character mage = CharacterFactory.createMage("Gandalf");

// Team 2: Two LLMs (different providers)
Character archer = CharacterFactory.createArcher("Legolas");
Character rogue = CharacterFactory.createRogue("Shadow");

Map<Character, Player> playerMap = Map.of(
    warrior, new HumanPlayer(),
    mage, new RuleBasedPlayer(),
    archer, new LLMPlayer(openAiChatClient, "GPT-4o"),
    rogue, new LLMPlayer(anthropicChatClient, "Claude-Sonnet-4.5")
);
```

<v-click>

## Watch Them Battle!

Two different AI models making strategic decisions

</v-click>

---
background: https://cover.sli.dev
---

# Security: API Keys

**Never commit API keys to Git!**

```yaml
# ❌ BAD
spring.ai.openai.api-key: sk-proj-abc123...

# ✅ GOOD
spring.ai.openai.api-key: ${OPENAI_API_KEY}
```

---

# Public Key Infrastructure (PKI)

**Understanding cryptographic keys and security**

<v-clicks>

## Why PKI Matters

- Secures communication over untrusted networks
- Enables authentication and authorization
- Provides data integrity and non-repudiation
- Foundation of modern web security (HTTPS, SSH, etc.)

## Key Concepts

- **Public Key**: Shared openly, used for encryption
- **Private Key**: Kept secret, used for decryption
- **Mathematical relationship** between key pairs

</v-clicks>

---

# Asymmetric Encryption

How public/private key pairs work

```mermaid
flowchart LR
    A[Alice] -->|1. Encrypt with<br/>Bob's Public Key| B[Message]
    B -->|2. Encrypted Message| C[Bob]
    C -->|3. Decrypt with<br/>Bob's Private Key| D[Original Message]

    style A fill:#90EE90
    style C fill:#87CEEB
    style B fill:#FFE4B5
```

<v-clicks>

## Key Properties
- Anyone can encrypt with public key
- Only private key holder can decrypt
- Computationally infeasible to derive private from public

</v-clicks>

---

# PKI Use Case 1: Confidentiality

Encrypting messages so only recipient can read

```mermaid
sequenceDiagram
    participant Alice
    participant Bob

    Bob->>Alice: Bob's Public Key
    Alice->>Bob: Encrypted Message
    Bob->>Bob: Decrypt with Private Key
```

<v-clicks>

**Process:**
1. Bob shares his public key
2. Alice encrypts with Bob's public key
3. Only Bob's private key can decrypt

</v-clicks>

---

# PKI Use Case 2: Digital Signatures

Proving message authenticity and integrity

```mermaid
sequenceDiagram
    participant Alice
    participant Bob

    Alice->>Bob: Public Key
    Alice->>Bob: Message + Signature
    Bob->>Bob: Verify with Public Key
```

<v-clicks>

**Process:**
1. Alice shares her public key
2. Alice signs message with private key
3. Bob verifies signature with Alice's public key

**Provides:** Authentication, Integrity, Non-repudiation

</v-clicks>

---

# Digital Signatures: How It Works

Split into sender and receiver processes

**Sender (Alice):**
```mermaid
flowchart LR
    A[Message] -->|Hash| B[Hash Value]
    B -->|Sign with<br/>Private Key| C[Signature]
    A --> D[Send: Message + Signature]
    C --> D
```

**Receiver (Bob):**
```mermaid
flowchart LR
    E[Message] -->|Hash| F[New Hash]
    G[Signature] -->|Decrypt with<br/>Public Key| H[Original Hash]
    F --> I{Match?}
    H --> I
    I -->|Yes| J[✓ Valid]
    I -->|No| K[✗ Invalid]

    style J fill:#90EE90
    style K fill:#ffcccc
```

---

# PKI Use Case 3: Non-Repudiation

Preventing denial of message origin

<v-clicks>

## The Problem
- Alice sends a signed contract to Bob
- Later, Alice claims she never sent it
- Can we prove Alice sent it?

## The Solution: Digital Signatures
- Alice signs with her **private key** (only she has)
- Bob verifies with Alice's **public key**
- Alice cannot deny signing (she's the only one with private key)

## Real-World Examples
- Legal contracts and agreements
- Financial transactions
- Code signing (software authenticity)

</v-clicks>

---

# Message Integrity

Any change to message produces different hash

```mermaid
flowchart LR
    A[Message: 'Hello'] -->|Hash| B[abc123]
    C[Modified: 'Goodbye'] -->|Hash| D[xyz789]
    B -.->|Different| E[❌ Tampered]
    D -.->|Different| E

    style E fill:#ffcccc
```

**Key Insight:** Different hash → signature fails → tamper detected

---
background: https://cover.sli.dev
---

# Cost Considerations

**OpenAI GPT-4o:** ~$2.50/$10 per M tokens

**Anthropic Claude:** ~$3/$15 per M tokens

<v-clicks>

## Assignment 6
- Per game: $0.02-0.10
- Total: < $1.00

## Cost Control
- Use mini models
- Limit max_tokens

</v-clicks>

---

# Testing Strategies

## Phase 1: No LLMs
```bash
./gradlew run  # No API keys needed
```

## Phase 2: Single LLM
```bash
export OPENAI_API_KEY=sk-...
./gradlew run
```

## Phase 3: Both LLMs
```bash
export OPENAI_API_KEY=sk-...
export ANTHROPIC_API_KEY=sk-ant-...
./gradlew run
```

---

# Mocking ChatClient

Unit test without API calls

```java
@Test
void testLLMPlayer() {
    ChatClient mockClient = mock(ChatClient.class);
    when(mockClient.prompt()).thenReturn(mockPromptSpec);
    when(mockPromptSpec.user(any())).thenReturn(mockCallSpec);
    when(mockCallSpec.call()).thenReturn(mockCallResult);
    when(mockCallResult.content()).thenReturn("""
        {"action": "attack", "target": "enemy", "reasoning": "test"}
        """);

    LLMPlayer player = new LLMPlayer(mockClient, "TestModel");
    GameCommand command = player.decideAction(self, allies, enemies, state);

    assertThat(command).isInstanceOf(AttackCommand.class);
}
```

---

# Observability

Monitor LLM usage

```yaml
spring.ai.chat.client.observations:
  include-prompt: false  # Security
  include-completion: true
```

<v-clicks>

## Monitor
- Request latency
- Token usage
- Error rates
- Cost per request

**Tools:** Actuator, Micrometer, logs

</v-clicks>

---

# Best Practices: Configuration

<v-clicks>

- Use environment variables for keys
- Configure multiple providers
- Set temperature and max_tokens

</v-clicks>

---

# Best Practices: Error Handling

<v-clicks>

- Always catch exceptions
- Provide fallback behavior
- Log errors for debugging

</v-clicks>

---

# Best Practices: Prompts

<v-clicks>

- Be specific and clear
- Provide context
- Request structured output (JSON)
- Include strategic guidance

</v-clicks>

---

# Best Practices: Security

<v-clicks>

- Never commit API keys
- Validate user input
- Monitor for abuse
- Don't log sensitive data

</v-clicks>

---

# Best Practices: Testing

<v-clicks>

- Test without LLMs first
- Mock ChatClient in unit tests
- Gradually add complexity
- Monitor costs

</v-clicks>

---

# Best Practices: Architecture

<v-clicks>

- Use design patterns (Strategy, Adapter)
- Dependency injection
- Separation of concerns

</v-clicks>

---
layout: default
---

# Resources

## Documentation
- Spring AI: docs.spring.io/spring-ai/reference/
- OpenAI: platform.openai.com/docs
- Anthropic: docs.anthropic.com/

## Assignment 6
- Complete README in repo
- Example interactions
- Troubleshooting guide

## Support
- Office hours: Wed 1:30-3:00 PM
- Email: kkousen@trincoll.edu

---
layout: center
class: text-center
---

# Questions?

## Next Session
Thursday: Assignment 6 walkthrough and live coding

<div class="pt-12">
  <span class="text-sm opacity-50">
    CPSC 310: Software Design | Fall 2025
  </span>
</div>
