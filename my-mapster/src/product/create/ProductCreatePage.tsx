import { useNavigate } from "react-router-dom";
import { IProductCreate, ICategoryItem } from "./types.ts";
import { Button, Form, Input, Row, Upload, Select } from "antd";
import TextArea from "antd/es/input/TextArea";
import { UploadChangeParam } from "antd/es/upload";
import { PlusOutlined } from '@ant-design/icons';
import http_common from "../../http_common.ts";
import {useEffect, useState} from "react";

const { Option } = Select;

const ProductCreatePage = () => {
    const navigate = useNavigate();
    const [form] = Form.useForm();
    const [categoryOptions, setCategoryOptions] = useState<ICategoryItem[]>([]);

    useEffect(() => {
        http_common.get("/api/categories/getAllCategories")
            .then(response => {
                console.log('Response from API:', response);
                setCategoryOptions(response.data);
            })
            .catch(error => console.error('Error fetching categories:', error));
    }, []);

    const onHandlerSubmit = async (values: IProductCreate) => {
        try {
            const formData = new FormData();
            formData.append("name", values.name);
            formData.append("description", values.description);

            if (values.category_id !== undefined) {
                formData.append("category_id", values.category_id.toString());
            }

            if (values.price !== undefined) {
                formData.append("price", values.price.toString());
            }

            if (values.files) {
                values.files.forEach((file, index) => {
                    formData.append(`files[${index}]`, file.originFileObj);
                });
            }

            await http_common.post("/api/products", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            navigate('/');
        } catch (ex) {
            console.error("Exception creating product", ex);
        }
    }


    return (
        <>
            <h1>Create Product</h1>
            <Row gutter={16}>
                <Form
                    form={form}
                    onFinish={onHandlerSubmit}
                    layout="vertical"
                    style={{
                        minWidth: '100%',
                        display: 'flex',
                        flexDirection: 'column',
                        justifyContent: 'center',
                        padding: 20,
                    }}
                >
                    <Form.Item
                        label="Name"
                        name="name"
                        rules={[
                            { required: true, message: "This field is required!" },
                            { min: 3, message: "Minimum length is 3 characters" },
                        ]}
                    >
                        <Input autoComplete="name" />
                    </Form.Item>

                    <Form.Item
                        label="Description"
                        name="description"
                        rules={[
                            { required: true, message: "This field is required!" },
                            { min: 10, message: "Minimum length is 10 characters" },
                        ]}
                    >
                        <TextArea />
                    </Form.Item>

                    <Form.Item
                        label="Price"
                        name="price"
                    >
                        <Input autoComplete="price" />
                    </Form.Item>

                    <Form.Item
                        label="Category"
                        name="category_id"
                        rules={[{ required: true, message: "Please select a category!" }]}
                    >
                        <Select>
                            {categoryOptions.map((category) => (
                                <Option key={category.id} value={category.id}>
                                    {category.name}
                                </Option>
                            ))}
                        </Select>
                    </Form.Item>

                    <Form.Item
                        name="files"
                        label="Photos"
                        valuePropName="fileList"
                        getValueFromEvent={(e: UploadChangeParam) => e.fileList}
                        rules={[{ required: true, message: 'Please select photos!' }]}
                    >
                        <Upload
                            showUploadList={{ showPreviewIcon: false }}
                            beforeUpload={() => false}
                            accept="image/*"
                            listType="picture-card"
                            maxCount={5}
                        >
                            <div>
                                <PlusOutlined />
                                <div style={{ marginTop: 8 }}>Upload</div>
                            </div>
                        </Upload>
                    </Form.Item>

                    <Row style={{ display: 'flex', justifyContent: 'center' }}>
                        <Button style={{ margin: 10 }} type="primary" htmlType="submit">
                            Create
                        </Button>
                        <Button style={{ margin: 10 }} htmlType="button" onClick={() => navigate('/')}>
                            Cancel
                        </Button>
                    </Row>
                </Form>
            </Row>
        </>
    );
}

export default ProductCreatePage;