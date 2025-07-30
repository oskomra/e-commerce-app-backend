package pl.pjatk.Stepify.product.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.pjatk.Stepify.exception.ResourceNotFoundException;
import pl.pjatk.Stepify.product.dto.productColor.ProductColorCreateDTO;
import pl.pjatk.Stepify.product.dto.productColor.ProductColorDTO;
import pl.pjatk.Stepify.product.dto.productColor.ProductColorUpdateDTO;
import pl.pjatk.Stepify.product.mappers.ProductColorMapper;
import pl.pjatk.Stepify.product.model.Product;
import pl.pjatk.Stepify.product.model.ProductColor;
import pl.pjatk.Stepify.product.repository.ProductColorRepository;
import pl.pjatk.Stepify.product.repository.ProductRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class ProductColorService {

    private final ProductColorRepository productColorRepository;
    private final ProductColorMapper productColorMapper;
    private final ProductRepository productRepository;
    private final Cloudinary cloudinary;


    public ProductColorDTO findProductColorById(long id) {
        ProductColor productColor = productColorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductColor with id " + id + " not found"));

        return productColorMapper.toDto(productColor);
    }

    public ProductColorDTO addProductColorById(long id, ProductColorCreateDTO productColorCreateDTO, MultipartFile imageFile) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        ProductColor productColor = productColorMapper.toEntity(productColorCreateDTO);
        productColor.setProduct(product);

        String modelName = product.getModelName().replaceAll("\\s+", "_");
        String colorName = productColorCreateDTO.getColor().replaceAll("\\s+", "_");
        String newFileName = modelName + "_" + colorName;

        Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(),
                ObjectUtils.asMap(
                        "public_id", "products/" + newFileName, // Folder + filename in Cloudinary
                        "overwrite", true,
                        "resource_type", "image"
                ));

        String imageUrl = (String) uploadResult.get("secure_url");

        productColor.setImages(List.of(imageUrl));


        productColor = productColorRepository.save(productColor);
        return productColorMapper.toDto(productColor);
    }


    public ProductColorDTO patchProductColorById(long id, ProductColorUpdateDTO productColorUpdateDTO) {
        ProductColor productColor = productColorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        productColorMapper.patch(productColor, productColorUpdateDTO);

        productColorRepository.save(productColor);
        return productColorMapper.toDto(productColor);
    }

    public void deleteProductColorById(long id) {
        if (productColorRepository.existsById(id)) {
            productColorRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Color not found with id " + id);
        }
    }



    public ProductColor findColorById(Product product, long colorId) {
        if (product.getColors() != null) {
            return product.getColors().stream()
                    .filter(c -> c.getId() == colorId)
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Color not found for this product"));
        } else {
            throw new ResourceNotFoundException("Color not found for this product");
        }
    }
    


}
