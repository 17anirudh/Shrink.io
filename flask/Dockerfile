FROM python:3.13.3-alpine3.22

WORKDIR /app

COPY . .

RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 5400

CMD ["python", "app.py"]