import tkinter as tk
from tkinter import messagebox
import random

# Main Game Class
class NumberGame:
    def __init__(self, root):
        self.root = root
        self.root.title("Number Guessing Game")
        self.root.geometry("400x400")
        self.round = 1
        self.attempts = 0
        self.max_attempts = 5
        self.score = 0
        self.target = random.randint(1, 100)
        self.setup_gui()

    def setup_gui(self):
        # Title
        self.title_label = tk.Label(self.root, text="Number Guessing Game", font=("Helvetica", 18))
        self.title_label.pack(pady=10)
        
        # Instructions
        self.instruction_label = tk.Label(self.root, text="Guess the number between 1 and 100", font=("Helvetica", 12))
        self.instruction_label.pack(pady=10)

        # Guess Entry
        self.guess_entry = tk.Entry(self.root, font=("Helvetica", 14), justify="center")
        self.guess_entry.pack(pady=10)

        # Submit Button
        self.submit_button = tk.Button(self.root, text="Submit Guess", font=("Helvetica", 14), command=self.check_guess)
        self.submit_button.pack(pady=10)

        # Feedback Label
        self.feedback_label = tk.Label(self.root, text="", font=("Helvetica", 14))
        self.feedback_label.pack(pady=10)

        # Attempts left
        self.attempts_label = tk.Label(self.root, text=f"Attempts Left: {self.max_attempts - self.attempts}", font=("Helvetica", 12))
        self.attempts_label.pack(pady=10)

        # Score Label
        self.score_label = tk.Label(self.root, text=f"Score: {self.score}", font=("Helvetica", 12))
        self.score_label.pack(pady=10)

        # Play Again Button
        self.play_again_button = tk.Button(self.root, text="Play Again", font=("Helvetica", 12), command=self.play_again)
        self.play_again_button.pack(pady=10)
        self.play_again_button.config(state="disabled")

    def check_guess(self):
        try:
            guess = int(self.guess_entry.get())
            if guess < 1 or guess > 100:
                raise ValueError
        except ValueError:
            messagebox.showwarning("Invalid Input", "Please enter a valid number between 1 and 100.")
            return
        
        self.attempts += 1
        if guess == self.target:
            self.feedback_label.config(text="Correct! You guessed the number!")
            self.score += 10 - self.attempts
            self.score_label.config(text=f"Score: {self.score}")
            self.end_round(won=True)
        elif guess < self.target:
            self.feedback_label.config(text="Too low! Try again.")
        else:
            self.feedback_label.config(text="Too high! Try again.")
        
        self.attempts_label.config(text=f"Attempts Left: {self.max_attempts - self.attempts}")
        
        if self.attempts >= self.max_attempts:
            self.feedback_label.config(text=f"Out of attempts! The correct number was {self.target}.")
            self.end_round(won=False)

    def end_round(self, won):
        self.submit_button.config(state="disabled")
        self.play_again_button.config(state="normal")
        if won:
            messagebox.showinfo("Round Over", "Congratulations, you won the round!")
        else:
            messagebox.showinfo("Round Over", "Better luck next time!")
        
    def play_again(self):
        self.round += 1
        self.attempts = 0
        self.target = random.randint(1, 100)
        self.feedback_label.config(text="")
        self.attempts_label.config(text=f"Attempts Left: {self.max_attempts - self.attempts}")
        self.guess_entry.delete(0, 'end')
        self.submit_button.config(state="normal")
        self.play_again_button.config(state="disabled")

# Main Application Loop
if __name__ == "__main__":
    root = tk.Tk()
    game = NumberGame(root)
    root.mainloop()
