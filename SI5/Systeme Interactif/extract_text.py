from pathlib import Path
from pypdf import PdfReader
import sys

sys.stdout.reconfigure(encoding='utf-8')

for pdf_path in sorted(Path('cours').glob('*.pdf')):
    reader = PdfReader(str(pdf_path))
    text = '\n'.join(page.extract_text() or '' for page in reader.pages)
    print(f"=== {pdf_path.name} ===")
    print(text[:4000])
    print('\n' + '-'*80 + '\n')
