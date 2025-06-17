from flask import Flask, redirect, render_template
from mysql.connector import connect

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

@app.errorhandler(404)
def error():
    return render_template('error.html')

@app.route('/<string:key>')
def handle_redirect(key: str):
    print(key)
    try:
        connection = connect(
            host='localhost',
            port=3306,
            database='uri',
            user='root',
            password='root@2004'
        )
        cursor = connection.cursor()
        cursor.execute("""
                CREATE TABLE IF NOT EXISTS url (
                    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    pack VARCHAR(20),
                    link TEXT,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
            """)
        query = 'SELECT link FROM url WHERE pack = %s'
        cursor.execute(query, (key,))
        result = cursor.fetchall()

        if result:
            return redirect(result[0][0])  
        else:
            return render_template('error.html')

    except Exception as e:
        print(f'Exception: {e}')
        return render_template('error.html')
    finally:
        cursor.close()
        connection.close()

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5400)