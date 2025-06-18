from flask import Flask, redirect, render_template
from sqlalchemy import create_engine, text

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/<string:key>')
def handle_redirect(key: str):
    try:
        DATABASE_URL = "mysql+pymysql://root:sdbYUGACuTRFNNkEMmSZUNMHeoaleEyl@shinkansen.proxy.rlwy.net:55957/railway"
        connection = create_engine(DATABASE_URL)
        result = ""
        with connection.connect() as conn:
            query = text("SELECT link FROM url WHERE pack = :key")
            response = conn.execute(query, {"key": key}).fetchone()
            result = response[0]
        if result:
            return redirect(result), 302
        else:
            return render_template('error.html'), 404

    except Exception as e:
        print(f'Exception: {e}')
        app.logger.info(e)
        return render_template('error.html'), 404


@app.errorhandler(404)
def error():
    print('404')
    return render_template('error.html')

@app.errorhandler(500)
def error2():
    print('500')
    return render_template('error.html')


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5400, debug=True)