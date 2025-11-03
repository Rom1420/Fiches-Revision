from pathlib import Path
from pypdf import PdfReader

output_dir = Path('extracted_text')
output_dir.mkdir(exist_ok=True)

for pdf_path in sorted(Path('cours').glob('*.pdf')):
    reader = PdfReader(str(pdf_path))
    text = '\n'.join(page.extract_text() or '' for page in reader.pages)
    target = output_dir / (pdf_path.stem + '.txt')
    target.write_text(text, encoding='utf-8')
    print(f'Wrote {target}')
