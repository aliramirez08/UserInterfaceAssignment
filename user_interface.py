import tkinter as tk
from tkinter import Menu, scrolledtext
from datetime import datetime
import random
import os
import sys

class UserInterfaceApp:
    def __init__(self, root):
        self.root = root
        self.root.title("User Interface I")

        # Create menu bar
        menu_bar = Menu(self.root)
        self.root.config(menu=menu_bar)

        # Create a menu
        file_menu = Menu(menu_bar, tearoff=0)
        menu_bar.add_cascade(label="Menu", menu=file_menu)

        # Add menu items
        file_menu.add_command(label="Show Date and Time", command=self.show_datetime)
        file_menu.add_command(label="Save to log.txt", command=self.save_to_log)
        file_menu.add_command(label="Random Green Hue", command=self.random_green_hue)
        file_menu.add_command(label="Exit", command=self.root.quit)

        # Create a text area
        self.text_area = scrolledtext.ScrolledText(self.root, wrap=tk.WORD, width=40, height=10)
        self.text_area.pack(padx=10, pady=10)

    def show_datetime(self):
        now = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        self.text_area.insert(tk.END, f"{now}\n")

    def save_to_log(self):
        with open("log.txt", "w") as file:
            file.write(self.text_area.get("1.0", tk.END).strip())

    def random_green_hue(self):
        green_shade = '#{:02x}{:02x}{:02x}'.format(
            random.randint(0, 100), random.randint(150, 255), random.randint(0, 100)
        )
        self.root.configure(bg=green_shade)
        self.text_area.insert(tk.END, f"Background set to: {green_shade}\n")

if __name__ == "__main__":
    root = tk.Tk()
    app = UserInterfaceApp(root)
    root.mainloop()
