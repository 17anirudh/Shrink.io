from flask import Flask, redirect, render_template
from mysql.connector import connect

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/<string:key>')
def handle_redirect(key: str):
    print(key)
    try:
        connection = connect(
            host='shinkansen.proxy.rlwy.net',
            port=55957,
            user='root',
            password='sdbYUGACuTRFNNkEMmSZUNMHeoaleEyl',
            database='railway'
        )
        print('Connected')
        cursor = connection.cursor()
        query = 'SELECT link FROM url WHERE pack = %s'
        cursor.execute(query, (key,))
        result = cursor.fetchall()
        print(f"Query result: {result}")
        if result:
            return redirect(result[0][0]), 302
        else:
            return render_template('error.html'), 404

    except Exception as e:
        print(f'Exception: {e}')
        return render_template('error.html'), 404
    finally:
        if 'cursor' in locals():
            cursor.close()
        if 'connection' in locals():
            connection.close()


@app.errorhandler(404)
def error():
    print('404')
    return render_template('error.html')

@app.errorhandler(500)
def error2():
    print('500')
    return render_template('error.html')


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5400)