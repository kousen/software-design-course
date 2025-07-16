package edu.trincoll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("A stack")
public class NestedTestsExample {
    
    Stack<Object> stack;
    
    @Nested
    @DisplayName("when new")
    class WhenNew {
        
        @BeforeEach
        void createNewStack() {
            stack = new Stack<>();
        }
        
        @Test
        @DisplayName("is empty")
        void isEmpty() {
            assertThat(stack).isEmpty();
        }
        
        @Test
        @DisplayName("throws EmptyStackException when popped")
        void throwsExceptionWhenPopped() {
            assertThatThrownBy(() -> stack.pop())
                    .isInstanceOf(java.util.EmptyStackException.class);
        }
        
        @Test
        @DisplayName("throws EmptyStackException when peeked")
        void throwsExceptionWhenPeeked() {
            assertThatThrownBy(() -> stack.peek())
                    .isInstanceOf(java.util.EmptyStackException.class);
        }
        
        @Nested
        @DisplayName("after pushing an element")
        class AfterPushing {
            
            String anElement = "test element";
            
            @BeforeEach
            void pushAnElement() {
                stack.push(anElement);
            }
            
            @Test
            @DisplayName("is no longer empty")
            void isNotEmpty() {
                assertThat(stack).isNotEmpty();
            }
            
            @Test
            @DisplayName("returns the element when popped and is empty")
            void returnElementWhenPopped() {
                assertThat(stack.pop()).isEqualTo(anElement);
                assertThat(stack).isEmpty();
            }
            
            @Test
            @DisplayName("returns the element when peeked but remains not empty")
            void returnElementWhenPeeked() {
                assertThat(stack.peek()).isEqualTo(anElement);
                assertThat(stack).isNotEmpty();
            }
        }
    }
}