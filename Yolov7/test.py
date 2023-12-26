from flask import Flask, request, jsonify, send_file
from flask_sqlalchemy import SQLAlchemy
from PIL import Image
import io
import os
import torch
from werkzeug.security import generate_password_hash, check_password_hash
import logging
from sqlalchemy.orm import relationship

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://root:qwer1234@localhost:3306/login'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class BlogPost(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(100), nullable=False)
    content = db.Column(db.Text, nullable=False)
    username = db.Column(db.String(50), db.ForeignKey('user.username'), nullable=False)
    user = relationship('User', back_populates='blog_posts')
    
class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(50), unique=True, nullable=False)
    password = db.Column(db.String(60), nullable=False)
    blog_posts = relationship('BlogPost', back_populates='user')

UPLOAD_FOLDER = 'uploads'
ALLOWED_EXTENSIONS = {'jpg', 'jpeg'}
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

def load_model1():
    model_name = find_model1()
    model = torch.hub.load("WongKinYiu/yolov7", 'custom', model_name)
    model.eval()
    return model

def find_model1():
    for f in os.listdir():
        if f.endswith(".pt"):
            if(f=="best.pt"):
                return f
    logger.error("이 디렉토리에 모델 파일을 넣어주세요!")

def process_prediction1(img):
    try:
        model = load_model2()
        imgs = [img]
        results = model(imgs, size=256)

        result_image_folder = os.path.join(app.config['UPLOAD_FOLDER'], 'result_image')
        os.makedirs(result_image_folder, exist_ok=True)

        result_image_path = os.path.join(result_image_folder, 'image0.jpg')
        results.save(save_dir=result_image_folder)

        with open(result_image_path, 'rb') as result_image_file:
            result_img_bytes = result_image_file.read()

        return result_img_bytes
    except Exception as e:
        logger.error(f"이미지 처리 중 오류 발생: {str(e)}")
        return None

def load_model2():
    model_name = find_model2()
    model = torch.hub.load("WongKinYiu/yolov7", 'custom', model_name)
    model.eval()
    return model

def find_model2():
    for f in os.listdir():
        if f.endswith(".pt"):
            if(f=="pepper_best.pt"):
                return f
    logger.error("이 디렉토리에 모델 파일을 넣어주세요!")

def process_prediction2(img):
    try:
        model = load_model2()
        imgs = [img]
        results = model(imgs, size=256)

        result_image_folder = os.path.join(app.config['UPLOAD_FOLDER'], 'result_image')
        os.makedirs(result_image_folder, exist_ok=True)

        result_image_path = os.path.join(result_image_folder, 'image0.jpg')
        results.save(save_dir=result_image_folder)

        with open(result_image_path, 'rb') as result_image_file:
            result_img_bytes = result_image_file.read()

        return result_img_bytes
    except Exception as e:
        logger.error(f"이미지 처리 중 오류 발생: {str(e)}")
        return None

def load_model3():
    model_name = find_model3()
    model = torch.hub.load("WongKinYiu/yolov7", 'custom', model_name)
    model.eval()
    return model

def find_model3():
    for f in os.listdir():
        if f.endswith(".pt"):
            if(f=="cucumber_best.pt"):
                return f
    logger.error("이 디렉토리에 모델 파일을 넣어주세요!")

def process_prediction3(img):
    try:
        model = load_model3()
        imgs = [img]
        results = model(imgs, size=256)

        result_image_folder = os.path.join(app.config['UPLOAD_FOLDER'], 'result_image')
        os.makedirs(result_image_folder, exist_ok=True)

        result_image_path = os.path.join(result_image_folder, 'image0.jpg')
        results.save(save_dir=result_image_folder)

        with open(result_image_path, 'rb') as result_image_file:
            result_img_bytes = result_image_file.read()

        return result_img_bytes
    except Exception as e:
        logger.error(f"이미지 처리 중 오류 발생: {str(e)}")
        return None

def load_model4():
    model_name = find_model4()
    model = torch.hub.load("WongKinYiu/yolov7", 'custom', model_name)
    model.eval()
    return model

def find_model4():
    for f in os.listdir():
        if f.endswith(".pt"):
            if(f=="greenonion_best.pt"):
                return f
    logger.error("이 디렉토리에 모델 파일을 넣어주세요!")

def process_prediction4(img):
    try:
        model = load_model4()
        imgs = [img]
        results = model(imgs, size=256)

        result_image_folder = os.path.join(app.config['UPLOAD_FOLDER'], 'result_image')
        os.makedirs(result_image_folder, exist_ok=True)

        result_image_path = os.path.join(result_image_folder, 'image0.jpg')
        results.save(save_dir=result_image_folder)

        with open(result_image_path, 'rb') as result_image_file:
            result_img_bytes = result_image_file.read()

        return result_img_bytes
    except Exception as e:
        logger.error(f"이미지 처리 중 오류 발생: {str(e)}")
        return None

@app.route('/login', methods=['POST'])
def login():
    try:
        data = request.get_json(force=True)
        if not data:
            logger.error('Invalid JSON data received')
            return jsonify({'status': 'error', 'message': '잘못된 JSON 데이터'}), 400

        username = data.get('username')
        password = data.get('password')
        
        logger.info(f"Received login request for username: {username}")

        if not username or not password:
            logger.error('Missing username or password in the request')
            return jsonify({'status': 'error', 'message': '사용자 이름과 비밀번호는 필수입니다'}), 400

        user = User.query.filter_by(username=username).first()
        print('user:',user.username)
        if (user.password==password):
            if(user.username==username):
                logger.info('Login successful')
                return jsonify({'status': 'success', 'message': '로그인 성공'})
        else:
            logger.error('Invalid credentials')
            return jsonify({'status': 'error', 'message': '아이디 또는 비밀번호가 올바르지 않습니다.'}), 401
    except Exception as e:
        logger.error(f"로그인 중 오류 발생: {str(e)}")
        return jsonify({'status': 'error', 'message': '서버 오류'}), 500

@app.route('/signup', methods=['POST'])
def signup():
    try:
        data = request.get_json(force=True)
        if not data:
            logger.error('Invalid JSON data received')
            return jsonify({'status': 'error', 'message': '잘못된 JSON 데이터'}), 400

        username = data.get('username')
        password = data.get('password')
        logger.info(f"Received signup request for username: {username}")

        if not username or not password:
            logger.error('Missing username or password in the request')
            return jsonify({'status': 'error', 'message': '사용자 이름과 비밀번호는 필수입니다'}), 400

        existing_user = User.query.filter_by(username=username).first()
        if existing_user:
            logger.error('Username already exists')
            return jsonify({'status': 'error', 'message': '이미 존재하는 사용자 이름입니다'}), 400

        new_user = User(username=username, password=password)

        db.session.add(new_user)
        db.session.commit()

        logger.info('Signup successful')
        return jsonify({'status': 'success', 'message': '가입 성공'})
    except Exception as e:
        db.session.rollback()
        logger.error(f"가입 중 오류 발생: {str(e)}")
        return jsonify({'status': 'error', 'message': '서버 오류'}), 500

@app.route('/upload_predict1', methods=['POST'])
def upload_and_predict1():
    try:
        if 'file' not in request.files:
            return jsonify({'error': '파일 부분이 없습니다'}), 400

        file = request.files['file']

        if file.filename == '':
            return jsonify({'error': '선택된 파일이 없습니다'}), 400

        if file and allowed_file(file.filename):
            img_bytes = file.read()
            img = Image.open(io.BytesIO(img_bytes))

            result_img_bytes = process_prediction1(img)

            if result_img_bytes:
                return send_file(io.BytesIO(result_img_bytes), mimetype='image/jpeg')
            else:
                return jsonify({'error': '이미지 처리 중 오류가 발생했습니다'}), 500
        else:
            return jsonify({'error': '잘못된 파일 확장자'}), 400
    except Exception as e:
        logger.error(f"이미지 업로드 및 예측 중 오류 발생: {str(e)}")
        return jsonify({'status': 'error', 'message': '서버 오류'}), 500
    except Exception as e:
        logger.error(f"이미지 업로드 및 예측 중 오류 발생: {str(e)}")
        return jsonify({'status': 'error', 'message': '서버 오류'}), 500

@app.route('/upload_predict2', methods=['POST'])
def upload_and_predict2():
    try:
        if 'file' not in request.files:
            return jsonify({'error': '파일 부분이 없습니다'}), 400

        file = request.files['file']

        if file.filename == '':
            return jsonify({'error': '선택된 파일이 없습니다'}), 400

        if file and allowed_file(file.filename):
            img_bytes = file.read()
            img = Image.open(io.BytesIO(img_bytes))

            result_img_bytes = process_prediction2(img)

            if result_img_bytes:
                return send_file(io.BytesIO(result_img_bytes), mimetype='image/jpeg')
            else:
                return jsonify({'error': '이미지 처리 중 오류가 발생했습니다'}), 500
        else:
            return jsonify({'error': '잘못된 파일 확장자'}), 400
    except Exception as e:
        logger.error(f"이미지 업로드 및 예측 중 오류 발생: {str(e)}")
        return jsonify({'status': 'error', 'message': '서버 오류'}), 500

@app.route('/upload_predict3', methods=['POST'])
def upload_and_predict3():
    try:
        if 'file' not in request.files:
            return jsonify({'error': '파일 부분이 없습니다'}), 400

        file = request.files['file']

        if file.filename == '':
            return jsonify({'error': '선택된 파일이 없습니다'}), 400

        if file and allowed_file(file.filename):
            img_bytes = file.read()
            img = Image.open(io.BytesIO(img_bytes))

            result_img_bytes = process_prediction3(img)

            if result_img_bytes:
                return send_file(io.BytesIO(result_img_bytes), mimetype='image/jpeg')
            else:
                return jsonify({'error': '이미지 처리 중 오류가 발생했습니다'}), 500
        else:
            return jsonify({'error': '잘못된 파일 확장자'}), 400
    except Exception as e:
        logger.error(f"이미지 업로드 및 예측 중 오류 발생: {str(e)}")
        return jsonify({'status': 'error', 'message': '서버 오류'}), 500

@app.route('/upload_predict4', methods=['POST'])
def upload_and_predict4():
    try:
        if 'file' not in request.files:
            return jsonify({'error': '파일 부분이 없습니다'}), 400

        file = request.files['file']

        if file.filename == '':
            return jsonify({'error': '선택된 파일이 없습니다'}), 400

        if file and allowed_file(file.filename):
            img_bytes = file.read()
            img = Image.open(io.BytesIO(img_bytes))

            result_img_bytes = process_prediction4(img)

            if result_img_bytes:
                return send_file(io.BytesIO(result_img_bytes), mimetype='image/jpeg')
            else:
                return jsonify({'error': '이미지 처리 중 오류가 발생했습니다'}), 500
        else:
            return jsonify({'error': '잘못된 파일 확장자'}), 400
    except Exception as e:
        logger.error(f"이미지 업로드 및 예측 중 오류 발생: {str(e)}")
        return jsonify({'status': 'error', 'message': '서버 오류'}), 500

@app.route('/blogbox', methods=['POST'])
def blogbox():
    try:
        # Extract blog information from the request
        title = request.form.get('title')
        content = request.form.get('content')
        username = request.form.get('blogKey')
        # Retrieve the user's blog key from the login/signup database
        user = User.query.filter_by(username=username).first()
        if not user:
            return jsonify({'error': '사용자를 찾을 수 없습니다'}), 404

        blog_key = username

        # Save the blog information to the database
        blog_post = BlogPost(title=title, content=content, username=username)
        db.session.add(blog_post)
        db.session.commit()

        return jsonify({'status': 'success', 'message': '블로그 정보가 성공적으로 저장되었습니다'})
    except Exception as e:
        logger.error(f"블로그 정보 업로드 중 오류 발생: {str(e)}")
        return jsonify({'status': 'error', 'message': '서버 오류'}), 500

# Endpoint to get blog posts
@app.route('/blog_posts', methods=['GET'])
def get_blog_posts():
    try:
        key = request.args.get('key')  # Get key (username) from query parameters

        # Filter posts by key (username)
        if key:
            filtered_posts = BlogPost.query.filter_by(username=key).all()
            # Convert the filtered posts to a list of dictionaries
            filtered_posts_dict = [{'title': post.title} for post in filtered_posts]
            return jsonify(filtered_posts_dict)
        else:
            # If no key is provided, return all blog posts
            all_posts = BlogPost.query.all()
            all_posts_dict = [{'title': post.title} for post in all_posts]
            return jsonify(all_posts_dict)
    except Exception as e:
        # Exception handling
        print(str(e))
        return jsonify({"error": "An error occurred"}), 500

# Endpoint to get blog posts
@app.route('/view_post', methods=['GET'])
def blog_posts():
    try:
        key = request.args.get('id')  # Get key (username) from query parameters

        # Filter posts by key (username)
        if key:
            filtered_posts = BlogPost.query.filter_by(id=key).all()
            # Convert the filtered posts to a list of dictionaries
            filtered_posts_dict = [{'title': post.title, 'content': post.content} for post in filtered_posts]
            return jsonify(filtered_posts_dict)
        else:
            # If no key is provided, return all blog posts
            all_posts = BlogPost.query.all()
            all_posts_dict = [{'title': post.title, 'content': post.content} for post in all_posts]
            return jsonify(all_posts_dict)
    except Exception as e:
        # Exception handling
        print(str(e))
        return jsonify({"error": "An error occurred"}), 500

if __name__ == '__main__':
    with app.app_context():
        db.create_all()

    app.run(host='0.0.0.0', port=5000, debug=True)
