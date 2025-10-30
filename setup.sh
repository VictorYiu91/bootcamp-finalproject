#!/bin/bash
if type deactivate &> /dev/null; then
    echo "🔁 Deactivating environment..."
    deactivate
fi
if [ -d "bootcamp-env" ]; then
    echo "🔁 Removing old virtual environment (if any)..."
    rm -rf bootcamp-env
fi
echo "📦 Creating new virtual environment..."
python -m venv bootcamp-env
echo "✅ Activating virtual environment..."
# Check if running on Windows (Git Bash) or Unix-like system
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" ]]; then
    source bootcamp-env/Scripts/activate
else
    source bootcamp-env/bin/activate
fi
pip install --upgrade pip
pip install -r requirements.txt
python -m ipykernel install --user --name=bootcamp-env --display-name "Python (bootcamp-env)"
pip --version
python --version
jupyter --version
echo "✅ Environment Setup complete."