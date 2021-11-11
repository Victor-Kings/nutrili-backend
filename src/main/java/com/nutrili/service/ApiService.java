package com.nutrili.service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import com.nutrili.exception.SomethingWentWrongException;
import com.nutrili.external.database.repository.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiService {

    @Autowired
    DatasetRepository datasetRepository;

    @Autowired
    ImageAnnotatorClient imageAnnotatorClient;

    public List<String> getLabel(MultipartFile pic) throws IOException {
        AnnotateImageResponse response = analizeImage(pic.getResource(), ImageContext.newBuilder().build(), Feature.Type.LABEL_DETECTION);

        System.out.println(response);

        return response.getLabelAnnotationsList().stream().filter((label)->
                datasetRepository.findByEnglishName(label.getDescription()).isPresent()
        ).map(label->datasetRepository.findByEnglishName(label.getDescription()).get().getName()).collect(Collectors.toList());
    }

    private AnnotateImageResponse analizeImage(Resource imageResource, ImageContext imageContext, Feature.Type... featureTypes) throws IOException {

        ByteString imgBytes;
        try {
            imgBytes = ByteString.readFrom(imageResource.getInputStream());
        } catch (IOException var10) {
            throw new SomethingWentWrongException();
        }

        Image image = Image.newBuilder().setContent(imgBytes).build();

        List<Feature> featureList = (List) Arrays.stream(featureTypes).map((featureType) -> {
            return Feature.newBuilder().setType(featureType).setMaxResults(20).build();
        }).collect(Collectors.toList());
        BatchAnnotateImagesRequest request = BatchAnnotateImagesRequest.newBuilder().addRequests(AnnotateImageRequest.newBuilder().addAllFeatures(featureList).setImageContext(ImageContext.getDefaultInstance()).setImage(image)).build();
        BatchAnnotateImagesResponse batchResponse = this.imageAnnotatorClient.batchAnnotateImages(request);
        List<AnnotateImageResponse> annotateImageResponses = batchResponse.getResponsesList();
        if (!annotateImageResponses.isEmpty()) {
            return (AnnotateImageResponse)annotateImageResponses.get(0);
        } else {
            throw new SomethingWentWrongException();
        }

    }

}
